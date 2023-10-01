package com.fixmia.rag.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fixmia.rag.annotations.IsUser;
import com.fixmia.rag.dtos.UserDTO;
import com.fixmia.rag.entities.JTI;
import com.fixmia.rag.entities.User;
import com.fixmia.rag.entities.UserType;
import com.fixmia.rag.services.JTIService;
import com.fixmia.rag.util.*;
import com.fixmia.rag.util.hibernate.AddRow;
import com.fixmia.rag.util.hibernate.DeleteRow;
import com.fixmia.rag.util.hibernate.LoadData;
import com.fixmia.rag.util.hibernate.RowChecker;
import io.fusionauth.jwt.domain.JWT;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.glassfish.jersey.server.mvc.Viewable;

import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

@IsUser
@Path("/")
public class AuthController {

    @Inject
    private JwtUtil jwtUtil;


    @POST
    @Path("/register")
    public String registerServiceProvider(@FormParam("token") String tokenString,
                                          @FormParam("firstName") String firstName,
                                          @FormParam("lastName") String lastName,
                                          @FormParam("description") String description,
                                          @FormParam("price") String price,
                                          @FormParam("serviceCategoryId") String serviceCategoryId,
                                          @FormDataParam("image") InputStream uploadedInputStream,
                                          @FormDataParam("image") FormDataContentDisposition fileDetail
    ) {
        JWT token = jwtUtil.maybeToken(tokenString);
        if (token != null) {
            System.out.println("token is " + token);
            System.out.println("first name is " + firstName);
            System.out.println("last name is " + lastName);
            System.out.println("description is " + description);
            System.out.println("price " + price);
            System.out.println("service provider id " + serviceCategoryId);
            System.out.println(fileDetail.getFileName());

            String email = jwtUtil.getUserEmailFromToken(tokenString);

            return "ok";
        } else {


            return "not ok";
        }
    }

    @Path("/loginuser")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response loginUser(UserDTO dto) {
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode arrayNode = mapper.createArrayNode();
        ObjectNode objectNode = mapper.createObjectNode();
        String email = dto.getEmail();
        char[] password = dto.getPassword();
        if (!InputValidator.inputEmailIsValid(email)) {
            objectNode.put("status", "Error");
            objectNode.put("message", "Email is invalid");
        } else if (!InputValidator.validPasswod(password)) {
            objectNode.put("status", "Error");
            objectNode.put("message", "Password is not valid");
        } else {
            RowChecker.addColumnNames("email");
            RowChecker.addColumnValues(email);
            if (RowChecker.rowExists("User")) {
                User user = LoadData.loadSingleData("User", "email", email);
                String salt = user.getSalt();
                String hashedPassword = user.getPassword();

                if (Encryption.verifyPassword(password, salt, hashedPassword)) {
                    UserDTO userDTO = new UserDTO();
                    userDTO.setEmail(email);
                    String token = jwtUtil.generateAccessTokenForUser(userDTO);
                    String rfToken = jwtUtil.generateRefreshTokenForUser(userDTO);
                    Long expiresIn = jwtUtil.getExpirationTimeInSeconds(token);

                    Dotenv dotenv = Dotenv.load();

                    ObjectNode userDetails = mapper.createObjectNode();
                    userDetails.put("email", email);
                    objectNode.put("status", "success");
                    objectNode.put("message", "login success");
                    objectNode.put("access_token", token);
                    objectNode.put("refresh_token", rfToken);
                    objectNode.put("expires_in", expiresIn);
                    objectNode.put("token_type", "bearer");
                    objectNode.put("user", userDetails);
                    UserType userType = LoadData.loadSingleData("UserType", "id", 1);
                    String userTypeCode = userType.getCode();

                    objectNode.put("user-type", userTypeCode);

                    arrayNode.add(objectNode);
                    System.out.println("ok success");
                    return Response.ok().entity(arrayNode).build();
                } else {
                    objectNode.put("status", "failed");
                    objectNode.put("message", "Wrong password");
                    arrayNode.add(objectNode);
                    return Response.status(Response.Status.NOT_ACCEPTABLE).entity(arrayNode).build();
                }

            } else {
                objectNode.put("status", "failed");
                objectNode.put("message", "user doesn't exist");
                arrayNode.add(objectNode);
                return Response.status(Response.Status.NOT_ACCEPTABLE).entity(arrayNode).build();
            }
        }
        objectNode.put("status", "Error");
        objectNode.put("message", "Couldn't process the request");
        arrayNode.add(objectNode);
        return Response.status(Response.Status.NOT_ACCEPTABLE).entity(arrayNode).build();

    }

    @Path("/validatetoken")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response validateToken(@FormParam("access_token") String accessToken, @FormParam("refresh_token") String refreshToken) {
        System.out.println("token are received");
        System.out.println("access token " + accessToken);
        JSONResponseBuilder builder = new JSONResponseBuilder();
        boolean isAccessTokenValid = jwtUtil.maybeToken(accessToken) != null;
        boolean isRefreshTokenValid = jwtUtil.maybeToken(refreshToken) != null;

        if (isAccessTokenValid && isRefreshTokenValid) {

            String accessJTIClaim = jwtUtil.getJTIFromToken(accessToken);
            String refreshJTIClaim = jwtUtil.getJTIFromToken(refreshToken);
            System.out.println("jti acces is " + accessJTIClaim);
            System.out.println("jti refresh is " + refreshJTIClaim);
            JTI accessJTI = new JTI(accessJTIClaim);
            JTI refreshJTI = new JTI(refreshJTIClaim);
            if (!JTIService.isJTIExists(accessJTIClaim)) {
                builder.addItems("status", "fail");
                builder.addItems("message", "not valid tokens");
                builder.addItems("access-token", accessToken);
                builder.addItems("refresh-token", refreshToken);

                ArrayNode arrayNode = builder.getJSON();
                return Response.status(Response.Status.NOT_ACCEPTABLE).entity(arrayNode).build();
            }
            if (!JTIService.isJTIExists(refreshJTIClaim)) {
                builder.addItems("status", "fail");
                builder.addItems("message", "not valid tokens");
                builder.addItems("access-token", accessToken);
                builder.addItems("refresh-token", refreshToken);

                ArrayNode arrayNode = builder.getJSON();
                return Response.status(Response.Status.NOT_ACCEPTABLE).entity(arrayNode).build();
            }
            builder.addItems("status", "success");
            builder.addItems("message", "tokens are valid");
            builder.addItems("access-token", accessToken);
            builder.addItems("refresh-token", refreshToken);
            ArrayNode arrayNode = builder.getJSON();
            return Response.ok().entity(arrayNode).build();

        } else if (!isAccessTokenValid && isRefreshTokenValid) {
            builder.addItems("status", "fail");
            builder.addItems("message", "expired access token");
            builder.addItems("access-token", accessToken);
            builder.addItems("refresh-token", refreshToken);
            ArrayNode arrayNode = builder.getJSON();
            return Response.ok().entity(arrayNode).build();
        } else {
            builder.addItems("status", "fail");
            builder.addItems("message", "not valid tokens");
            builder.addItems("access-token", accessToken);
            builder.addItems("refresh-token", refreshToken);

            ArrayNode arrayNode = builder.getJSON();
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity(arrayNode).build();
        }


    }

    @Path("/refresh-token")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response refreshTokenTest(@FormParam("refresh-token") String refreshToken) {
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode arrayNode = mapper.createArrayNode();
        ObjectNode objectNode = mapper.createObjectNode();
        JWT jwt = jwtUtil.maybeToken(refreshToken);
        if (jwt != null) {

            System.out.println("token valid is " + jwtUtil.isTokenValid(refreshToken));
            if (jwtUtil.isTokenValid(refreshToken)) {


                String email = jwtUtil.getUserEmailFromToken(refreshToken);
                UserDTO userDTO = new UserDTO();
                userDTO.setEmail(email);
                String newAccessToken = jwtUtil.generateAccessTokenForUser(userDTO);
                Long expiresIn = jwtUtil.getExpirationTimeInSeconds(newAccessToken);

                Dotenv dotenv = Dotenv.load();

                ObjectNode userDetails = mapper.createObjectNode();
                userDetails.put("email", email);
                objectNode.put("status", "success");
                objectNode.put("message", "token creation success");
                objectNode.put("access_token", newAccessToken);
                objectNode.put("refresh_token", refreshToken);
                objectNode.put("expires_in", expiresIn);
                objectNode.put("token_type", "bearer");
                objectNode.put("user", userDetails);

                UserType userType = (UserType) LoadData.loadSingleData("UserType", "id", 1);
                String userTypeCode = userType.getCode();

                objectNode.put("user-type", userTypeCode);
                arrayNode.add(objectNode);
                return Response.ok().entity(arrayNode).build();
            } else {
                objectNode.put("status", "Error");
                objectNode.put("message", "Invalid Refresh Token");
                arrayNode.add(objectNode);
                return Response.status(Response.Status.NOT_ACCEPTABLE).entity(arrayNode).build();

            }
        } else {
            System.out.println("refresh token is expired ");
            objectNode.put("status", "Error");
            objectNode.put("message", "Invalid Refresh Token");
            arrayNode.add(objectNode);
            System.out.println(arrayNode);
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity(arrayNode).build();
        }

    }

    @POST
    @Path("/logout")
    @Produces(MediaType.APPLICATION_JSON)
    public Response logout(@FormParam("access-token") String accessToken, @FormParam("refresh-token") String refreshToken) {
        boolean isAccessTokenValid = jwtUtil.maybeToken(accessToken) != null;
        boolean isRefreshTokenValid = jwtUtil.maybeToken(refreshToken) != null;
        JSONResponseBuilder builder = new JSONResponseBuilder();
        if (!isAccessTokenValid || !isRefreshTokenValid) {
            builder.addItems("status", "fail");
            builder.addItems("message", "not valid tokens");
            builder.addItems("access-token", accessToken);
            builder.addItems("refresh-token", refreshToken);

            ArrayNode arrayNode = builder.getJSON();
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity(arrayNode).build();
        } else {
            String accessJTIClaim = jwtUtil.getJTIFromToken(accessToken);
            String refreshJTIClaim = jwtUtil.getJTIFromToken(refreshToken);
            if (!JTIService.isJTIExists(accessJTIClaim)) {
                builder.addItems("status", "fail");
                builder.addItems("message", "not valid tokens");
                builder.addItems("access-token", accessToken);
                builder.addItems("refresh-token", refreshToken);

                ArrayNode arrayNode = builder.getJSON();
                return Response.status(Response.Status.NOT_ACCEPTABLE).entity(arrayNode).build();
            }
            if (!JTIService.isJTIExists(refreshJTIClaim)) {
                builder.addItems("status", "fail");
                builder.addItems("message", "not valid tokens");
                builder.addItems("access-token", accessToken);
                builder.addItems("refresh-token", refreshToken);

                ArrayNode arrayNode = builder.getJSON();
                return Response.status(Response.Status.NOT_ACCEPTABLE).entity(arrayNode).build();
            }
            JTIService.deleteJTIByClaim(accessJTIClaim);
            JTIService.deleteJTIByClaim(refreshJTIClaim);
            builder.addItems("status", "success");
            builder.addItems("message", "logged out successfully");
            ArrayNode arrayNode = builder.getJSON();
            return Response.ok().entity(arrayNode).build();
        }
    }

    @POST
    @Path("/signupuser")
    @Consumes(MediaType.APPLICATION_JSON)
    public String signupUser(UserDTO dto) throws NoSuchAlgorithmException {
        boolean signupStatus = false;
        boolean validationStatus = false;
        String username = dto.getUsername();
        String email = dto.getEmail();
        char[] password = dto.getPassword();
        char[] confirmPassword = dto.getConfirmPassword();
        if (!InputValidator.inputTextIsValid(username)) {
            return "Non-Exception:Employee user name is invalid";
        } else if (!InputValidator.inputEmailIsValid(email)) {
            return "Non-Exception:User email is invalid";
        } else if (RowChecker.rowExists("User", "email", email)) {
            return "Non-Exception:This email already exists";
        } else if (!InputValidator.validPasswod(password)) {
            return "Non-Exception:Password is invalid";
        } else if (!(InputValidator.areCharArraysEqual(password, confirmPassword))) {
            return "Non-Exception:Passwords don't match";
        } else {
            validationStatus = true;
        }
        if (validationStatus) {

//            password salt
            String hashedPassword = "";
            String salt = "";
            salt = Encryption.getSalt();
            hashedPassword = Encryption.get_SHA_512_SecurePassword(password, salt);
            System.out.println("hashed password is " + hashedPassword);
            System.out.println("password is  " + Arrays.toString(password));
            System.out.println("salt is " + salt);
            password = null;
            confirmPassword = null;

            UserType userType = new UserType();
            userType.setId(1);

            User user = new User();
            user.setPassword(hashedPassword);
            user.setSalt(salt);
            user.setEmail(email);
            user.setUsername(username);
            user.setUserType(userType);
            boolean addRowStatus = AddRow.addRow(user);

            if (addRowStatus) {
                return ReturnMessage.successMessage("User added successfully");
            } else {
                return ReturnMessage.nonException("User couldn't be added");
            }
        } else {
            return ReturnMessage.nonException("Couldn't validate inputs sorry");
        }
    }


}
