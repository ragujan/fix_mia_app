package com.fixmia.rag.controllers;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("/")
public class ClientAPI {
    @GET
    @Path("/googleapi")
    public String getGoogleApi() {
        Dotenv dotenv = Dotenv.load();
        String googleID = dotenv.get("GOOGLE_ID");
        return googleID;
    }
}
