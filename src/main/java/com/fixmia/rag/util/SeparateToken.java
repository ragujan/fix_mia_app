package com.fixmia.rag.util;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.HttpHeaders;

public class SeparateToken {
    public static String separate(ContainerRequestContext containerRequestContext){
        String header = containerRequestContext.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        String token = header.split(" ")[1];
        return token;
    }
}
