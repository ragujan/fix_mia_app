package com.fixmia.rag.controllers;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("/")
public class ClientAPI {
    @GET
    @Path("/google-api")
    public String getGoogleApi(){
        return "337084451495-b1tda8u3401dmtqcpcfsrlgprnrs0op8.apps.googleusercontent.com";
    }
}
