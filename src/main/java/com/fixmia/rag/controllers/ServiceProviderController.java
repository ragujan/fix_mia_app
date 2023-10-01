package com.fixmia.rag.controllers;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fixmia.rag.entities.User;
import com.fixmia.rag.entities.service_provider.ServiceProvider;
import com.fixmia.rag.entities.service_provider.ServiceProviderCategory;
import com.fixmia.rag.entities.service_provider.ServiceProviderDescription;
import com.fixmia.rag.entities.service_provider.ServiceProviderPFP;
import com.fixmia.rag.services.ServiceProviderService;
import com.fixmia.rag.util.InputValidator;
import com.fixmia.rag.util.JSONResponseBuilder;
import com.fixmia.rag.util.JwtUtil;
import com.fixmia.rag.util.hibernate.AddRow;
import com.fixmia.rag.util.hibernate.LoadData;
import com.fixmia.rag.util.hibernate.RowChecker;
import com.sun.net.httpserver.HttpContext;
import io.fusionauth.jwt.domain.JWT;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.inject.Inject;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import java.io.*;
import java.util.List;

@Path("/service-provider")
public class ServiceProviderController {

    @Inject
    private JwtUtil jwtUtil;

    @Context
    private ServletContext servletContext;

    @Context
    private UriInfo uriInfo;

    private String getPFPUrl(String imageName){
        String contextPath = servletContext.getAttribute("BASE_URL").toString() ;
        String domain = uriInfo.getBaseUri().getHost();
        String scheme = uriInfo.getBaseUri().getScheme();
        String port = Integer.toString(uriInfo.getBaseUri().getPort());
        String pfpFilePath = "service_provider_pfp_images";
        String origin = scheme+"://"+domain+":"+port+"/"+contextPath;
        String pfpPath = origin+pfpFilePath;
        String imagePath = pfpPath+"/"+imageName;
        System.out.println("image path "+imagePath);
        return imagePath;
    }
    @GET
    @Path("/test")
    public String doGet() {
        String contextPath = servletContext.getAttribute("BASE_URL").toString() ;
        String domain = uriInfo.getBaseUri().getHost();
        String scheme = uriInfo.getBaseUri().getScheme();
        String port = Integer.toString(uriInfo.getBaseUri().getPort());
        String pfpFilePath = "service_provider_pfp_images";
        String origin = scheme+"://"+domain+":"+port+"/"+contextPath;
        String pfpPath = origin+pfpFilePath;
        String image = "/OobUBL6REhyGZGb5lFYYbig_smoke.jpg";
        String imagePath = pfpPath+"/"+image;
        System.out.println("image path "+imagePath);
        return "Ok do get test";
    }
    @Path("/view-all-service-providers")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllServiceProviders(){
        JSONResponseBuilder builder = new JSONResponseBuilder();
        List<Object[]> serviceProviderList = LoadData.loadAll("ServiceProvider");
        for (Object[] serviceProviders :serviceProviderList
        ) {
            ServiceProvider serviceProvider = (ServiceProvider) serviceProviders[0];
            System.out.println(serviceProvider.getId());
            ServiceProviderPFP serviceProviderPFP = LoadData.
                    loadSingleData("ServiceProviderPFP","serviceProvider",serviceProvider);

            String pfpURL = getPFPUrl(serviceProviderPFP.getPfpUrl());
//
            ServiceProviderDescription serviceProviderDescription = LoadData.
                    loadSingleData("ServiceProviderDescription","serviceProvider",serviceProvider);
            String description = serviceProviderDescription.getDescription();

            String fname = serviceProvider.getFirstName();
            String lname = serviceProvider.getLastName();
            String price = Double.toString(serviceProvider.getPrice());
            String email = serviceProvider.getUser().getEmail();
            System.out.println("email is "+email);
            System.out.println("pfp url is "+pfpURL);


//            builder.addItems(Long.toString(category.getId()), category.getCategoryName());
        }
        ArrayNode arrayNode = builder.getJSON();
        return Response.ok().entity(arrayNode).build();
    }

    @Path("/get-service-categories")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCategories() {
        JSONResponseBuilder builder = new JSONResponseBuilder();
        List<Object[]> categoriesList = LoadData.loadAll("ServiceProviderCategory");

        for (Object[] categories : categoriesList
        ) {
            ServiceProviderCategory category = (ServiceProviderCategory) categories[0];
            builder.addItems(Long.toString(category.getId()), category.getCategoryName());
        }
        ArrayNode arrayNode = builder.getJSON();
        return Response.ok().entity(arrayNode).build();
    }

    @POST
    @Path("/register")
    public String registerServiceProvider(@FormParam("token") String tokenString,
                                          @FormParam("firstName") String firstName,
                                          @FormParam("lastName") String lastName,
                                          @FormParam("description") String description,
                                          @FormParam("price") String price,
                                          @FormParam("serviceCategoryId") String serviceCategoryId,

                                          @FormDataParam("image") InputStream uploadedInputStream,
                                          @FormDataParam("image") FormDataContentDisposition fileDetail
    ) {
        JWT token = jwtUtil.maybeToken(tokenString);
        boolean inputsAreValid = true;

        if (!InputValidator.inputTextIsValid(firstName)) {
            inputsAreValid = false;
            return "Invalid firstname";
        } else if (!InputValidator.inputTextIsValid(lastName)) {
            inputsAreValid = false;
            return "Invalid lastname";
        } else if (!InputValidator.inputPriceIsValid(price)) {
            inputsAreValid = false;
            return "Invalid price";
        } else if (!InputValidator.inputNumberIsValid(serviceCategoryId)) {
            inputsAreValid = false;
            return "Invalid service category id";
        } else if (!InputValidator.inputTextIsValid(description)) {
            inputsAreValid = false;
            return "Invalid description";
        }
        if (token != null && inputsAreValid) {
            System.out.println("token is " + token);
            System.out.println("first name is " + firstName);
            System.out.println("last name is " + lastName);
            System.out.println("description is " + description);
            System.out.println("price " + price);
            System.out.println("service provider id " + serviceCategoryId);
            System.out.println(fileDetail.getFileName());

            String email = jwtUtil.getUserEmailFromToken(tokenString);

            boolean isUserFound = RowChecker.rowExists("User", "email", email);
            if (isUserFound) {
//              get the user email from User Entity
                User user = LoadData.loadSingleData("User", "email", email);
//              get service provider category entity using category id
                ServiceProviderCategory serviceProviderCategory = new ServiceProviderCategory();
                serviceProviderCategory.setId(1L);

//              generate a random id
                String generatedId = ServiceProviderService.generateServiceProviderRandomId();
//              service provider details entity
                ServiceProvider serviceProvider = new ServiceProvider();
                serviceProvider.setFirstName(firstName);
                serviceProvider.setLastName(lastName);
                serviceProvider.setUser(user);
                serviceProvider.setGeneratedId(generatedId);
                serviceProvider.setBannedStatus(false);
                serviceProvider.setActiveStatus(true);
                serviceProvider.setPrice(Double.parseDouble(price));
                serviceProvider.setServiceProviderCategory(serviceProviderCategory);

//              profile picture process
                String pfpName = fileDetail.getFileName();
                String generatedPFPPath = ServiceProviderService.generatePFPPath(pfpName);
                ServiceProviderPFP serviceProviderPFP = new ServiceProviderPFP();
                serviceProviderPFP.setServiceProvider(serviceProvider);
                serviceProviderPFP.setPfpUrl(generatedPFPPath);

                ServiceProviderDescription serviceProviderDescription = new ServiceProviderDescription();
                serviceProviderDescription.setServiceProvider(serviceProvider);
                serviceProviderDescription.setDescription(description);

                boolean serviceProviderRegisterStatus = AddRow.addRow(serviceProvider);
                AddRow.addRow(serviceProviderDescription);
                if (serviceProviderRegisterStatus) {
                    AddRow.addRow(serviceProviderPFP);
                    try {

                        String uploadDirectory = Dotenv.load().get("SERVICE_PROVIDER_PFP_DIRECTORY");
                        OutputStream outputStream = new FileOutputStream(new File(uploadDirectory + generatedPFPPath));

                        int read;
                        byte[] bytes = new byte[1024];

                        while ((read = uploadedInputStream.read(bytes)) != -1) {
                            outputStream.write(bytes, 0, read);
                        }
                        outputStream.flush();
                        outputStream.close();
                        return "success";
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        return "image upload failed";
                    }
                } else {
                    return "failed";
                }
            } else {
                return "user is not found";
            }
        } else {


            return "invalid token";
        }
    }
}
