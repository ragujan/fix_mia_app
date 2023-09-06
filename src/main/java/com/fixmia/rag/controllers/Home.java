package com.fixmia.rag.controllers;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.glassfish.jersey.server.mvc.Viewable;

@Path("/")
public class Home {
    @GET
    public Viewable get(){
        return new Viewable("/homepage");
    }
}
