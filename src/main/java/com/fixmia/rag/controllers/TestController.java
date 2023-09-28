package com.fixmia.rag.controllers;


import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fixmia.rag.util.JSONResponseBuilder;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/")
public class TestController {
    @GET
    @Path("testpageget")
    public String doTest() {
        return "ok";
    }

    @POST
    @Path("testpage")
    @Produces(MediaType.APPLICATION_JSON)
    public Response doTestPost() {
        JSONResponseBuilder builder = new JSONResponseBuilder();
        builder.addItems("status", "success");
        builder.addItems("message", "test success");
        ArrayNode arrayNode = builder.getJSON();
        return Response.ok().entity(arrayNode).build();
    }

    @POST
    @Path("testpage2")
    @Produces(MediaType.APPLICATION_JSON)
    public Response doTestPost2() {
        JSONResponseBuilder builder = new JSONResponseBuilder();
        builder.addItems("status", "success");
        builder.addItems("message", "test success");
        ArrayNode arrayNode = builder.getJSON();
        return Response.ok().entity(arrayNode).build();
    }
}
