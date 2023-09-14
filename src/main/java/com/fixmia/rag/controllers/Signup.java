package com.fixmia.rag.controllers;

import com.fixmia.rag.annotations.IsUser;
import com.fixmia.rag.dtos.SignupDTO;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.LowLevelHttpRequest;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.server.mvc.Viewable;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

@IsUser
@Path("/")
public class Signup {

    @Context
    HttpServletRequest request;
    @GET
    @Path("/signup")
    public Viewable get() {
        return new Viewable("/frontend/signup");
    }
    @GET
    @Path("/login")
    public Viewable getlogin() {
        return new Viewable("/frontend/login");
    }
    @POST
    @Path("/signupuser")
    @Consumes(MediaType.APPLICATION_JSON)
    public String post(SignupDTO dto){
        boolean signupStatus = false;
        System.out.println("Email is "+dto.getEmail());
        return "Ok";
    }
    @POST
    @Path("signupgooglehome")
    public Viewable post(@FormParam("credential") String dataClientID) throws GeneralSecurityException, IOException {
        String CLIENT_ID = "337084451495-b1tda8u3401dmtqcpcfsrlgprnrs0op8.apps.googleusercontent.com";
        System.out.println("Data Client id is "+dataClientID);
        System.out.println("Hey a request has been made");
        HttpTransport transport = new NetHttpTransport() ;
        JsonFactory jsonFactory = new GsonFactory();
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                // Specify the CLIENT_ID of the app that accesses the backend:
                .setAudience(Collections.singletonList(CLIENT_ID))
                // Or, if multiple clients access the backend:
                //.setAudience(Arrays.asList(CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3))
                .build();
// (Receive idTokenString by HTTPS POST)
        GoogleIdToken idToken = verifier.verify(dataClientID);
        if (idToken != null) {
            Payload payload = idToken.getPayload();

            // Print user identifier
            String userId = payload.getSubject();
            System.out.println("User ID: " + userId);

            // Get profile information from payload
            String email = payload.getEmail();
            boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
            String name = (String) payload.get("name");
            String pictureUrl = (String) payload.get("picture");
            String locale = (String) payload.get("locale");
            String familyName = (String) payload.get("family_name");
            String givenName = (String) payload.get("given_name");

            // Use or store profile information
            // ...
            System.out.println("the verified email is "+email);
            System.out.println("user name is "+name);

            HttpSession session = request.getSession();
            session.setAttribute("username",name);
            session.setAttribute("email",email);

            return new Viewable("/homepage");
        } else {

            System.out.println("Invalid ID token.");
            return new Viewable("/frontend/signup");
        }

    }
}
