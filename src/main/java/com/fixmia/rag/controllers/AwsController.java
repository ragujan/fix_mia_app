package com.fixmia.rag.controllers;

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.core.waiters.WaiterResponse;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.model.HeadBucketRequest;
import software.amazon.awssdk.services.s3.model.HeadBucketResponse;
import software.amazon.awssdk.services.s3.model.ListBucketsRequest;
import software.amazon.awssdk.services.s3.model.ListBucketsResponse;
import software.amazon.awssdk.services.s3.model.DeleteBucketRequest;
import software.amazon.awssdk.services.s3.waiters.S3Waiter;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("/")
public class AwsController {
    @GET
    @Path("/aws-test")
    public String doGet() {
//        AWSCredentials credentials = new BasicAWSCredentials(
//                "<AWS accesskey>",
//                "<AWS secretkey>"
//        );
        return "aws";
    }

//    public static void main(String[] args) {
//        ProfileCredentialsProvider credentialsProvider = ProfileCredentialsProvider.create();
//        Region region = Region.AP_SOUTH_1;
//        S3Client s3 = S3Client.builder()
//                .region(region)
//                .credentialsProvider(credentialsProvider)
//                .build();
//        // List buckets
//        ListBucketsRequest listBucketsRequest = ListBucketsRequest.builder().build();
//        ListBucketsResponse listBucketsResponse = s3.listBuckets(listBucketsRequest);
//        listBucketsResponse.buckets().stream().forEach(x -> System.out.println(x.name()));
//
//
//
//
//    }
}
