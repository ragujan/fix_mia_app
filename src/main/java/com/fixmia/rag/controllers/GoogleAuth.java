package com.fixmia.rag.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fixmia.rag.dtos.UserDTO;
import com.fixmia.rag.entities.User;
import com.fixmia.rag.entities.UserType;
import com.fixmia.rag.util.JwtUtil;
import com.fixmia.rag.util.ReturnMessage;
import com.fixmia.rag.util.hibernate.AddRow;
import com.fixmia.rag.util.hibernate.RowChecker;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

@Path("/")
public class GoogleAuth {

    @Inject
    private JwtUtil jwtUtil;
    @POST
    @Path("googleoauthlogin")
    @Produces(MediaType.APPLICATION_JSON)
    public Response postLogin(@FormParam("credential") String credential)throws GeneralSecurityException, IOException {
        System.out.println("came here");
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode arrayNode = mapper.createArrayNode();
        ObjectNode objectNode = mapper.createObjectNode();

        GoogleIdToken idToken = verifyGoogleIdToken(credential);
        if (idToken != null) {
            System.out.println("user is here and in google ");
            GoogleIdToken.Payload payload = idToken.getPayload();

            String userId = payload.getSubject();

            // Get profile information from payload
            String email = payload.getEmail();
            // boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
            boolean isUserExists = RowChecker.rowExists("User","email",email);
            if(isUserExists){



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

                return Response.ok().entity(arrayNode).build();
            }else{
                objectNode.put("status", "failed");
                objectNode.put("message", "Not a google user");
                arrayNode.add(objectNode);
                System.out.println("not a google user");
                return Response.status(Response.Status.NOT_ACCEPTABLE).entity(arrayNode).build();
            }
        } else {

            objectNode.put("status", "failed");
            objectNode.put("message", "Google Couldn't verify you");
            arrayNode.add(objectNode);
            System.out.println("google couldn't verify you ");
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity(arrayNode).build();
        }
    }
    @POST
    @Path("googleoauthsignup")
    public String postSignUp(@FormParam("credential") String credential)throws GeneralSecurityException, IOException {

        GoogleIdToken idToken = verifyGoogleIdToken(credential);
        if (idToken != null) {
            GoogleIdToken.Payload payload = idToken.getPayload();

            String userId = payload.getSubject();

            // Get profile information from payload
            String email = payload.getEmail();
            // boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
            String name = (String) payload.get("name");
            boolean isUserExists = RowChecker.rowExists("User","email",email);
            if(isUserExists){
                return ReturnMessage.nonException("User is a registered user");
            }else{
                UserType userType = new UserType();
                userType.setId(1);

                User user = new User();
                user.setEmail(email);
                user.setUsername(name);
                user.setUserType(userType);
                boolean status = AddRow.addRow(user);
                if(status)
                    return ReturnMessage.successMessage("User added successfully");
                else
                    return ReturnMessage.nonException("Couldn't add the user");
            }
        } else {
            System.out.println(ReturnMessage.nonException("Invalid ID token."));
            return ReturnMessage.nonException("Invalid ID token.");
        }
    }

    public GoogleIdToken verifyGoogleIdToken(String credential) throws GeneralSecurityException, IOException {
        Dotenv dotenv = Dotenv.load();
        String googleID = dotenv.get("GOOGLE_ID");
        HttpTransport transport = new NetHttpTransport();
        JsonFactory jsonFactory = new GsonFactory();
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                .setAudience(Collections.singletonList(googleID))
                .build();


        GoogleIdToken idToken = verifier.verify(credential);
        return idToken;
    }


}
