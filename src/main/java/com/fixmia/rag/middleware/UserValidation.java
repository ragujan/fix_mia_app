package com.fixmia.rag.middleware;


import com.fixmia.rag.annotations.IsUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;

@Provider
@IsUser
//@PreMatching

public class UserValidation implements ContainerRequestFilter {
    @Context
    HttpServletRequest request;
    @Context
    HttpServletResponse response;

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        System.out.println("ok------IsUserImpl");
        HttpSession session = request.getSession();
        String path = containerRequestContext.getUriInfo().getPath();

        if (session.getAttribute("username") == null) {
            System.out.println("not a authenticated user");


//            containerRequestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity("Unauthenticated User").build());
        } else {

            if(path.equals("signup") || path.equals("signin")){
                containerRequestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            }else{

            }
        }

    }
}
