package com.fixmia.rag.controllers;

import com.fixmia.rag.annotations.IsUser;
import com.fixmia.rag.dtos.SignupDTO;
import com.fixmia.rag.entities.User;
import com.fixmia.rag.util.APIKeys;
import com.fixmia.rag.util.InputValidator;
import com.fixmia.rag.util.hibernate.AddRow;
import com.fixmia.rag.util.hibernate.RowChecker;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
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

    @POST
    @Path("/signupuser")
    @Consumes(MediaType.APPLICATION_JSON)
    public String post(SignupDTO dto) {
        boolean signupStatus = false;
        boolean validationStatus = false;
        String username = dto.getUsername();
        String email = dto.getEmail();
        String password = dto.getPassword();
        String confirmPassword = dto.getConfirmPassword();
        if (!InputValidator.inputTextIsValid(username)) {
            return "Non-Exception:Employee user name is invalid";
        } else if (!InputValidator.inputEmailIsValid(email)) {
            return "Non-Exception:User email is invalid";
        } else if ( RowChecker.rowExists("User", "email", email)) {
            return "Non-Exception:This email already exists";
        } else if (!InputValidator.validPasswod(password)) {
            return "Non-Exception:Password is invalid";
        } else if (!password.equals(confirmPassword)) {
            return "Non-Exception:Passwords don't match";
        } else {
            validationStatus = true;
        }
        if (validationStatus) {
            User user = new User();
            user.setEmail(email);
            user.setUsername(username);
            user.setPassword(password);
            boolean addRowStatus = AddRow.addRow(user);

            if (addRowStatus) {
                return "Non-Exception:User added successfully";
            } else {
                return "Non-Exception:User couldn't be added";
            }
        } else {
            return "Non-Exception:Error";
        }
    }

    @POST
    @Path("signupgooglehome")
    public Viewable post(@FormParam("credential") String dataClientID) throws GeneralSecurityException, IOException {
        String CLIENT_ID = APIKeys.getGoogleid();
        System.out.println("Data Client id is " + dataClientID);
        System.out.println("Hey a request has been made");
        HttpTransport transport = new NetHttpTransport();
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
            // boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
            String name = (String) payload.get("name");
            // String pictureUrl = (String) payload.get("picture");
            // String locale = (String) payload.get("locale");
            // String familyName = (String) payload.get("family_name");
            // String givenName = (String) payload.get("given_name");

            // Use or store profile information
            // ...
            System.out.println("the verified email is " + email);
            System.out.println("user name is " + name);

            HttpSession session = request.getSession();
            session.setAttribute("username", name);
            session.setAttribute("email", email);

            return new Viewable("/homepage");
        } else {

            System.out.println("Invalid ID token.");
            return new Viewable("/frontend/signup");
        }

    }
}
