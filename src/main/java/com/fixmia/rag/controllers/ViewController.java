package com.fixmia.rag.controllers;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.glassfish.jersey.server.mvc.Viewable;

public class ViewController {
    @GET
    @Path("/login")
    public Viewable loginView() {
        return new Viewable("/frontend/login");
    }

    @GET
    @Path("/signup")
    public Viewable signupView() {
        return new Viewable("/frontend/signup");
    }

}
