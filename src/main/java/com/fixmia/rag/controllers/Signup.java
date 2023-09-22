package com.fixmia.rag.controllers;

import com.fixmia.rag.annotations.IsUser;
import com.fixmia.rag.dtos.UserDTO;
import com.fixmia.rag.entities.User;
import com.fixmia.rag.util.Encryption;
import com.fixmia.rag.util.InputValidator;
import com.fixmia.rag.util.ReturnMessage;
import com.fixmia.rag.util.hibernate.AddRow;
import com.fixmia.rag.util.hibernate.RowChecker;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import org.glassfish.jersey.server.mvc.Viewable;

import jakarta.servlet.http.HttpServletRequest;

import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

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
    public String post(UserDTO dto) throws NoSuchAlgorithmException {
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
            System.out.println("hashed password is "+hashedPassword);
            System.out.println("password is  "+Arrays.toString(password));
            System.out.println("salt is "+salt);
            password = null;
            confirmPassword = null;


            User user = new User();
            user.setPassword(hashedPassword);
            user.setSalt(salt);
            user.setEmail(email);
            user.setUsername(username);
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
