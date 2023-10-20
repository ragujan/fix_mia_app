package com.fixmia.rag.controllers;


import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UploadObject {

    public static void main(String[] args) throws IOException {
        String bucketName = "testbucket3rag";
        String fileObjKeyName = "ragbag2.txt";
        String fileName = "C:\\Users\\ACER\\OneDrive\\Documents\\fixmia_viva_webapp\\fix_mia_app\\src\\main\\webapp\\service_provider_pfp_images\\abc.txt";
        fileName = "src/main/webapp/service_provider_pfp_images/abc.txt";
        ProfileCredentialsProvider credentialsProvider = ProfileCredentialsProvider.create();
        Region region = Region.EU_NORTH_1;
        S3Client s3 = S3Client.builder()
                .region(region)
                .credentialsProvider(credentialsProvider)
                .build();

        putS3Object(s3, bucketName, fileObjKeyName, fileName);

    }

    public static void putS3Object(S3Client s3, String bucketName, String objectKey, String objectPath) {
        try {
            Map<String, String> metadata = new HashMap<>();
            metadata.put("x-amz-meta-myVal", "test");
            PutObjectRequest putOb = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(objectKey)
                    .metadata(metadata)
                    .build();
//          This putOb works without compile error because builder method is not available
//            PutObjectRequest putOb = new PutObjectRequest(bucketName, objectKey, new File(objectPath));
            s3.putObject(putOb, RequestBody.fromFile(new File(objectPath)));
            System.out.println("Successfully placed " + objectKey + " into bucket " + bucketName);

        } catch (S3Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }
}

