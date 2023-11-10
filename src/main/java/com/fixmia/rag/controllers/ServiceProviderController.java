package com.fixmia.rag.controllers;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fixmia.rag.entities.User;
import com.fixmia.rag.entities.service_provider.ServiceProvider;
import com.fixmia.rag.entities.service_provider.ServiceProviderCategory;
import com.fixmia.rag.entities.service_provider.ServiceProviderDescription;
import com.fixmia.rag.entities.service_provider.ServiceProviderPFP;
import com.fixmia.rag.services.ServiceProviderService;
import com.fixmia.rag.util.AWS;
import com.fixmia.rag.util.InputValidator;
import com.fixmia.rag.util.JSONResponseBuilder;
import com.fixmia.rag.util.JwtUtil;
import com.fixmia.rag.util.hibernate.*;
import com.mysql.cj.result.Row;
import com.sun.net.httpserver.HttpContext;
import io.fusionauth.jwt.domain.JWT;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.inject.Inject;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.apache.commons.io.IOUtils;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import java.io.*;
import java.util.List;
import java.util.Set;

@Path("/service-provider")
public class ServiceProviderController {

    @Inject
    private JwtUtil jwtUtil;

    @Context
    private ServletContext servletContext;

    @Context
    private UriInfo uriInfo;

    private String getPFPUrl(String imageName) {
        String contextPath = servletContext.getAttribute("BASE_URL").toString();
        String domain = uriInfo.getBaseUri().getHost();
        String scheme = uriInfo.getBaseUri().getScheme();
        String port = Integer.toString(uriInfo.getBaseUri().getPort());
        String pfpFilePath = "service_provider_pfp_images";
        String origin = scheme + "://" + domain + ":" + port + contextPath;
        String pfpPath = origin + pfpFilePath;
        String imagePath = pfpPath + "/" + imageName;
        System.out.println("image path " + imagePath);
        return imagePath;
    }

    @GET
    @Path("/test")
    @Produces(MediaType.APPLICATION_JSON)
    public Response doGet1() {
        JSONResponseBuilder builder = new JSONResponseBuilder();
        builder.addItems("name", "bag");
        builder.addItems("age", "22");

        JSONResponseBuilder book = builder.createBuilder();
        book.addItems("name", "HObbit12");
        book.addItems("year", "2023");
        builder.addItems("book", book.getObjectNode());
        ArrayNode arrayNode = builder.getJSON();
        return Response.ok().entity(arrayNode).build();
    }

    @GET
    @Path("/test2")
    public String doGet2() {
        String contextPath = servletContext.getAttribute("BASE_URL").toString();
        String domain = uriInfo.getBaseUri().getHost();
        String scheme = uriInfo.getBaseUri().getScheme();
        String port = Integer.toString(uriInfo.getBaseUri().getPort());
        String pfpFilePath = "service_provider_pfp_images";
        String origin = scheme + ":/" + domain + ":" + port + "/" + contextPath;
        String pfpPath = origin + pfpFilePath;
        String image = "/OobUBL6REhyGZGb5lFYYbig_smoke.jpg";
        String imagePath = pfpPath + "/" + image;
        System.out.println("image path " + imagePath);
        return "Ok do get test";
    }

    @Path("/view-all-service-providers")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllServiceProviders() {
        JSONResponseBuilder builder = new JSONResponseBuilder();
        List<Object[]> serviceProviderList = LoadData.loadAll("ServiceProvider");
        int i = 0;
        for (Object[] serviceProviders : serviceProviderList
        ) {
            ServiceProvider serviceProvider = (ServiceProvider) serviceProviders[0];
            System.out.println(serviceProvider.getId());
            ServiceProviderPFP serviceProviderPFP = LoadData.
                    loadSingleData("ServiceProviderPFP", "serviceProvider", serviceProvider);

            String pfpURL = getPFPUrl(serviceProviderPFP.getPfpUrl());
//            pfpURL = Dotenv.load().get("SERVICE_PROVIDER_PFP_DIRECTORY")+serviceProviderPFP.getPfpUrl();
//
            ServiceProviderDescription serviceProviderDescription = LoadData.
                    loadSingleData("ServiceProviderDescription", "serviceProvider", serviceProvider);
            String description = serviceProviderDescription.getDescription();

            String fname = serviceProvider.getFirstName();
            String lname = serviceProvider.getLastName();
            String generatedId = serviceProvider.getGeneratedId();
            String price = Double.toString(serviceProvider.getPrice());
            String email = serviceProvider.getUser().getEmail();
            System.out.println("email is " + email);
            System.out.println("pfp url is " + pfpURL);
            JSONResponseBuilder builder1 = new JSONResponseBuilder();
            builder1.addItems("fname", fname);
            builder1.addItems("lname", lname);
            builder1.addItems("generated_id", generatedId);
            builder1.addItems("price", price);
            builder1.addItems("email", email);
            builder1.addItems("pfp_url", pfpURL);
            builder1.addItems("description", description);
            builder.addItems(Integer.toString(i), builder1.getObjectNode());
            i++;

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
    @Path("/category-register")
    public String addCategory(@FormParam("category_name") String categoryName) {
        boolean isExists = RowChecker.rowExists("ServiceProviderCategory", "categoryName", categoryName);
        if (!isExists) {
            ServiceProviderCategory serviceProviderCategory = new ServiceProviderCategory();
            serviceProviderCategory.setCategoryName(categoryName);
            AddRow.addRow(serviceProviderCategory);
            return "successfully added";
        } else {
            return "Error Failed to add";
        }

    }

    @POST
    @Path("/service-provider-update")
    public String updateServiceProvider(@FormDataParam("email") String email, @FormParam("first_name") String firstName, @FormParam("last_name") String lastName, @FormParam("price") String price) {
        boolean isExists = RowChecker.rowExists("User", "email", email);
        if (isExists) {
            User user = LoadData.loadSingleData("User", "email", email);
            isExists = RowChecker.rowExists("ServiceProvider", "user", user);
            if (isExists) {
                ServiceProvider serviceProvider = LoadData.loadSingleData("ServiceProvider", "user", user);
                System.out.println(serviceProvider.getLastName());
                serviceProvider.setFirstName(firstName);
                serviceProvider.setLastName(lastName);
                serviceProvider.setPrice(Double.parseDouble(price));
//                Set<ServiceProviderDescription> descriptions = serviceProvider.getServiceProviderDescriptions();

                UpdateRow.update(serviceProvider);

                return "ok SP exists";

            }
        }
        return "ok";


    }

    @POST
    @Path("/delete-service-provider")
    public String deleteServiceProvider(@FormParam("email") String email) {
        boolean isExists = RowChecker.rowExists("User", "email", email);
        if (isExists) {
            User user = LoadData.loadSingleData("User", "email", email);
            isExists = RowChecker.rowExists("ServiceProvider", "user", user);
            if (isExists) {
                ServiceProvider serviceProvider = LoadData.loadSingleData("ServiceProvider", "user", user);
                System.out.println(serviceProvider.getPrice());
                Set<ServiceProviderPFP> pfps = serviceProvider.getServiceProviderPFPS();
                Set<ServiceProviderDescription> descriptions = serviceProvider.getServiceProviderDescriptions();
                System.out.println("size is  " + pfps.size());
                System.out.println("de is  " + descriptions.size());

                return "deleted successfully";
            } else {
                return "not found ";
            }
        } else {
            return "Error Failed to delete";
        }

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
                Long receivedId = Long.parseLong(serviceCategoryId);
                serviceProviderCategory.setId(receivedId);

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
