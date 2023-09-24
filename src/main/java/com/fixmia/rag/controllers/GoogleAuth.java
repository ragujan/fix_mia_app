package com.fixmia.rag.controllers;

import com.fixmia.rag.entities.User;
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
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

@Path("/")
public class GoogleAuth {
    @POST
    @Path("googleoauthlogin")
    public String postLogin(@FormParam("credential") String credential)throws GeneralSecurityException, IOException {
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
                return ReturnMessage.successMessage("User is there");
            }else{
                return ReturnMessage.nonException("User couldn't be found");
            }
        } else {

            System.out.println(ReturnMessage.nonException("Invalid ID token."));
            return ReturnMessage.nonException("Invalid ID token.");
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
                User user = new User();
                user.setEmail(email);
                user.setUsername(name);
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
