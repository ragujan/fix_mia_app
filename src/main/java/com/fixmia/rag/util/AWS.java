package com.fixmia.rag.util;


import org.apache.commons.io.IOUtils;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.*;
import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class AWS {
    public static void uploadImage(String fileObjectKeyName, byte[] stream) throws IOException {
        String bucketName = "testbucket3rag";
        Region region = Region.EU_NORTH_1;
        S3Client s3 = S3Client.builder()
                .region(region)
                .build();

        PutObjectRequest objectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(fileObjectKeyName)
                .build();
//        s3.putObject(objectRequest, RequestBody.fromInputStream(stream, stream.available()));
        s3.putObject(objectRequest, RequestBody.fromBytes(stream));

    }
    public static void printByteArray(byte[] byteArray) {
        String fileContent = new String(byteArray);
        System.out.println("File Content:");
        System.out.println(fileContent);
    }

    public static List<Object> cloneInputStreamandSize(InputStream originalStream) throws IOException {
        int count = 0;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024]; // Adjust the buffer size as needed
        int bytesRead;
        while ((bytesRead = originalStream.read(buffer)) != -1) {
            byteArrayOutputStream.write(buffer, 0, bytesRead);
            count++;
        }
        byteArrayOutputStream.flush();

        List list = new LinkedList();
        list.add(count);
        list.add(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));
        return list;
    }
    public static InputStream cloneInputStream(InputStream originalStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024]; // Adjust the buffer size as needed
        int bytesRead;
        while ((bytesRead = originalStream.read(buffer)) != -1) {
            byteArrayOutputStream.write(buffer, 0, bytesRead);
        }
        byteArrayOutputStream.flush();

        return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
    }
    public static int getInputStreamLength(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int chunkBytesRead = 0;
        int length = 0;
        while ((chunkBytesRead = inputStream.read(buffer)) != -1) {
            length += chunkBytesRead;
        }
        return length;
    }
}
