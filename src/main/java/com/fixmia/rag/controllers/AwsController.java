package com.fixmia.rag.controllers;

import com.fixmia.rag.util.AWS;
import jakarta.ws.rs.POST;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import jakarta.ws.rs.Path;

import java.io.IOException;

@Path("/")
public class AwsController {

    @POST
    @Path("/uploadImage")
    public String uploadImage(
            @FormDataParam("image") byte[] uploadedInputStream,
            @FormDataParam("image") FormDataContentDisposition fileDetail
    ) throws IOException {
        AWS.uploadImage(fileDetail.getFileName(), uploadedInputStream);
        return "Ok";
    }

}
