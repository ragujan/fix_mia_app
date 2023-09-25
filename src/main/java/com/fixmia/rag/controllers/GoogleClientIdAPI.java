package com.fixmia.rag.controllers;

import com.fixmia.rag.annotations.IsGoogleApiRequest;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
@IsGoogleApiRequest
@Path("/")
public class GoogleClientIdAPI {
    @GET
    @Path("/googleapi")
    public String getGoogleApi() {
        Dotenv dotenv = Dotenv.load();
        String googleID = dotenv.get("GOOGLE_ID");
        return googleID;
    }
}
