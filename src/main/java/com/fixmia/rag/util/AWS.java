package com.fixmia.rag.util;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.*;


public class AWS {
    public static void uploadImage(String fileObjectKeyName, byte[] stream) throws IOException {
        String bucketName = "firstbuckettestrag";
//        Mumbai AP_SOUTH_1
        Region region = Region.AP_SOUTH_1;
        S3Client s3 = S3Client.builder()
                .region(region)
                .build();

        PutObjectRequest objectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(fileObjectKeyName)
                .build();
        s3.putObject(objectRequest, RequestBody.fromBytes(stream));

    }

}
