package com.fixmia.rag.controllers;

import com.fixmia.rag.dtos.UserDTO;
import com.fixmia.rag.entities.User;
import com.fixmia.rag.util.InputValidator;
import com.fixmia.rag.util.JwtUtil;
import com.fixmia.rag.util.ReturnMessage;
import com.fixmia.rag.util.hibernate.RowChecker;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.inject.Inject;
import org.glassfish.jersey.server.mvc.Viewable;

import com.fixmia.rag.annotations.IsUser;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;

@IsUser
@Path("/")
public class Login {
    @Inject
    private JwtUtil jwtUtil;

    @Path("/loginuser")
    @POST
    public String post(UserDTO dto) {

        String email = dto.getEmail();
        String password = dto.getPassword();
        if (!InputValidator.inputEmailIsValid(email)) {
            return ReturnMessage.nonException("Email is invalid");
        } else if (!InputValidator.validPasswod(password)) {
            return ReturnMessage.nonException("Not a Valid Password");
        } else {
            RowChecker.addColumnNames("email", "password");
            RowChecker.addColumnValues(email, password);
            if (RowChecker.rowExists("User")) {

                UserDTO userDTO = new UserDTO();
                userDTO.setEmail(email);
                String token = jwtUtil.generateAccessToken(userDTO);
                String rfToken = jwtUtil.generateRefreshToken(userDTO);

                return ReturnMessage.successMessage("User is there");
            } else {
                return ReturnMessage.nonException("User couldn't be found");
            }
        }
    }


    @GET
    @Path("/login")
    public Viewable getlogin() {
        return new Viewable("/frontend/login");
    }


}
