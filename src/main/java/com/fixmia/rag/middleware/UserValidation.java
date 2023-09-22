package com.fixmia.rag.middleware;


import com.fixmia.rag.annotations.IsUser;
import com.fixmia.rag.util.JwtUtil;
import com.fixmia.rag.util.hibernate.RowChecker;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;

@Provider
@IsUser
//@PreMatching
@Priority(1)
public class UserValidation implements ContainerRequestFilter {
    @Context
    HttpServletRequest request;
    @Context
    HttpServletResponse response;

    @Inject
    private JwtUtil jwtUtil;

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {

        String path = containerRequestContext.getUriInfo().getPath();
        Dotenv dotenv = Dotenv.load();
        String allowedOrigin = dotenv.get("ALLOWED_ORIGIN");

        String origin = containerRequestContext.getHeaderString("Origin");

        if (path.equals("loginuser") || path.equals("signupuser")) {
            System.out.println(containerRequestContext.getHeaders().getFirst(HttpHeaders.AUTHORIZATION));
//            this is to check if a token valid token exits from response if yes you don't have to allow them for the login
//            user
            if (containerRequestContext.getHeaders().getFirst(HttpHeaders.AUTHORIZATION) != null) {
                String header = containerRequestContext.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
                String token = header.split(" ")[1];
                System.out.println("Token is " + token);
//                check if the token is actually json formatted token
                if (jwtUtil.maybeToken(token) != null) {

                    String email = jwtUtil.getUserEmailFromToken(token);
                    boolean emailExists = RowChecker.rowExists("User", "email", email);
                    boolean isTokenValid = jwtUtil.validateToken(token);

                    if (emailExists && isTokenValid) {
                        System.out.println("This token is valid");
                        System.out.println("You can't just sign in ");
                        containerRequestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity("You are not allowed to login again ").build());
                    } else {
                        System.out.println("This is not a valid token");
                    }

                }
            }

        }


    }
}
