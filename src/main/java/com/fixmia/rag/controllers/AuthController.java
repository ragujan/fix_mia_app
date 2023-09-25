package com.fixmia.rag.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fixmia.rag.annotations.IsUser;
import com.fixmia.rag.dtos.UserDTO;
import com.fixmia.rag.entities.User;
import com.fixmia.rag.entities.UserType;
import com.fixmia.rag.util.Encryption;
import com.fixmia.rag.util.InputValidator;
import com.fixmia.rag.util.JwtUtil;
import com.fixmia.rag.util.ReturnMessage;
import com.fixmia.rag.util.hibernate.AddRow;
import com.fixmia.rag.util.hibernate.LoadData;
import com.fixmia.rag.util.hibernate.RowChecker;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.server.mvc.Viewable;

import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
@IsUser
@Path("/")
public class AuthController {
    @Inject
    private JwtUtil jwtUtil;

    @Path("/loginuser")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response loginUser(UserDTO dto) {
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode arrayNode = mapper.createArrayNode();
        ObjectNode objectNode = mapper.createObjectNode();
        System.out.println("came here ");
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
                    System.out.println("user is confirmed ");
                    UserDTO userDTO = new UserDTO();
                    userDTO.setEmail(email);
                    String token = jwtUtil.generateAccessTokenForUser(userDTO);
                    String rfToken = jwtUtil.generateRefreshTokenForUser(userDTO);
                    Long expiresIn = jwtUtil.getExpirationTimeInSeconds(token);

                    ObjectNode userDetails = mapper.createObjectNode();
                    userDetails.put("email", email);
                    objectNode.put("status", "success");
                    objectNode.put("message", "login success");
                    objectNode.put("access_token", token);
                    objectNode.put("refresh_token", rfToken);
                    objectNode.put("expires_in", expiresIn);
                    objectNode.put("token_type", "bearer");


                    objectNode.put("user", userDetails);
                    arrayNode.add(objectNode);
                    System.out.println("ok success");
                    return Response.ok().entity(arrayNode).build();
                } else {
                    System.out.println("User is not there");
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
        System.out.println("came after the everything hahah");
        objectNode.put("status", "Error");
        objectNode.put("message", "Couldn't process the request");
        arrayNode.add(objectNode);
        return Response.status(Response.Status.NOT_ACCEPTABLE).entity(arrayNode).build();

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
