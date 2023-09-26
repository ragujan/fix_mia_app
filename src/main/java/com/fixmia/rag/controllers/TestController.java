package com.fixmia.rag.controllers;


import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("/")
public class TestController {
    @GET
    @Path("testpage")
    public String doTest(){
        return "ok";
    }
}
