package com.fixmia.rag.controllers;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.glassfish.jersey.server.mvc.Viewable;
@Path("/")
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
    @GET
    @Path("/")
    public Viewable homeView(){
        return new Viewable("/homepage");
    }

    @GET
    @Path("/addcategory")
    public Viewable addServiceCategoryView(){
        return new Viewable("/addcategory");
    }

}
