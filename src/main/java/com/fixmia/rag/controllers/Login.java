package com.fixmia.rag.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fixmia.rag.dtos.UserDTO;
import com.fixmia.rag.entities.User;
import com.fixmia.rag.util.Encryption;
import com.fixmia.rag.util.InputValidator;
import com.fixmia.rag.util.JwtUtil;
import com.fixmia.rag.util.hibernate.LoadData;
import com.fixmia.rag.util.hibernate.RowChecker;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.server.mvc.Viewable;

import com.fixmia.rag.annotations.IsUser;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

import java.util.Arrays;

@IsUser
@Path("/")
public class Login {
    @Inject
    private JwtUtil jwtUtil;

    @Path("/loginuser")
    @POST
    public Response post(UserDTO dto) {

        ObjectMapper mapper = new ObjectMapper();
        ArrayNode arrayNode = mapper.createArrayNode();
        ObjectNode objectNode = mapper.createObjectNode();

        String email = dto.getEmail();
        char[] password = dto.getPassword();
        if (!InputValidator.inputEmailIsValid(email)) {
            objectNode.put("status", "Error");
            objectNode.put("message", "Email is invalid");
        } else if (!InputValidator.validPasswod(Arrays.toString(password))) {
            objectNode.put("status", "Error");
            objectNode.put("message", "Password is not valid");
        } else {
            RowChecker.addColumnNames("email");
            RowChecker.addColumnValues(email);
            if (RowChecker.rowExists("User")) {
                User user = LoadData.loadSingleData("User", "email", email);
                String salt = user.getSalt();
                String hashedPassword = user.getPassword();

                if(Encryption.verifyPassword(password,salt,hashedPassword)){
                    System.out.println("user is confirmed ");
                    UserDTO userDTO = new UserDTO();
                    userDTO.setEmail(email);
                    String token = jwtUtil.generateAccessToken(userDTO);
                    String rfToken = jwtUtil.generateRefreshToken(userDTO);
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
                    return Response.ok().entity(arrayNode).build();
                }else{
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
        objectNode.put("status", "Error");
        objectNode.put("message", "Couldn't process the request");
        arrayNode.add(objectNode);
        return Response.status(Response.Status.NOT_ACCEPTABLE).entity(arrayNode).build();

    }


    @GET
    @Path("/login")
    public Viewable getlogin() {
        return new Viewable("/frontend/login");
    }


}
