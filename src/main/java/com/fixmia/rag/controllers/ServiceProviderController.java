package com.fixmia.rag.controllers;

import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

@Path("/service-provider")
public class ServiceProviderController {
    @GET
    @Path("/test")
    public String doGet() {
        return "Ok do get test";
    }

    @POST
    @Path("/register")
    public String register(@FormParam("token") String token,
                           @FormParam("firstName") String firstName,
                           @FormParam("lastName") String lastName,
                           @FormParam("description")String description,
                           @FormParam("price") String price,
                           @FormParam("serviceCategoryId")String serviceCategoryId) {

        System.out.println("token is "+token);
        System.out.println("first name is "+firstName);
        System.out.println("last name is "+lastName);
        System.out.println("description is "+description);
        System.out.println("price "+price);
        System.out.println("service provider id "+serviceCategoryId);

        return "ok";
    }
}
