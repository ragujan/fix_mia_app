package com.fixmia.rag.controllers;

import com.fixmia.rag.entities.User;
import com.fixmia.rag.entities.service_provider.ServiceProvider;
import com.fixmia.rag.entities.service_provider.ServiceProviderCategory;
import com.fixmia.rag.entities.service_provider.ServiceProviderPFP;
import com.fixmia.rag.services.ServiceProviderService;
import com.fixmia.rag.util.InputValidator;
import com.fixmia.rag.util.JwtUtil;
import com.fixmia.rag.util.hibernate.AddRow;
import com.fixmia.rag.util.hibernate.LoadData;
import com.fixmia.rag.util.hibernate.RowChecker;
import io.fusionauth.jwt.domain.JWT;
import jakarta.inject.Inject;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import java.io.InputStream;

@Path("/service-provider")
public class ServiceProviderController {

    @Inject
    private JwtUtil jwtUtil;

    @GET
    @Path("/test")
    public String doGet() {
        return "Ok do get test";
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

                boolean serviceProviderRegisterStatus = AddRow.addRow(serviceProvider);
                if (serviceProviderRegisterStatus) {
                    AddRow.addRow(serviceProviderPFP);
                    return "success";
                }else{
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
