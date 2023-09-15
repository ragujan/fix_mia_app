package com.fixmia.rag.controllers;

import org.glassfish.jersey.server.mvc.Viewable;

import com.fixmia.rag.annotations.IsUser;
import com.fixmia.rag.dtos.LoginDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;

@IsUser
@Path("/")
public class Login {
    @Context
    HttpServletRequest httpServletRequest;

    @Path("loginuser")
    @POST
    public String post(LoginDTO dto) {

        return "Hey";
    }

    @GET
    @Path("/login")
    public Viewable getlogin() {
        return new Viewable("/frontend/login");
    }
}
