package com.fixmia.rag.middleware;


import com.fixmia.rag.annotations.IsUser;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.annotation.Priority;
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
@Priority(2)
public class GoogleAPIValidation implements ContainerRequestFilter {
    @Context
    HttpServletRequest request;
    @Context
    HttpServletResponse response;

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {

        String path = containerRequestContext.getUriInfo().getPath();
//        System.out.println("requested path is "+path);
        Dotenv dotenv = Dotenv.load();
        String allowedOrigin = dotenv.get("ALLOWED_ORIGIN");

        String origin = containerRequestContext.getHeaderString("Origin");
//        System.out.println("origin is  "+origin);
        if(path.equals("googleapi")  && (origin == null || !(origin.equals(allowedOrigin) || origin.equals("http://127.0.0.1:5173"))) ){
            containerRequestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity("Unauthorized origin").build());
        }


    }
}
