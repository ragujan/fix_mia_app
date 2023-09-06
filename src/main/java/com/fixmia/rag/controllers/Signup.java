package com.fixmia.rag.controllers;

import org.glassfish.jersey.server.mvc.Viewable;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("/")
public class Signup {
    @GET
    @Path("/signup")
    public Viewable get(){
        return new Viewable("/frontend/signup");
    }
}
