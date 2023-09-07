package com.fixmia.rag.filters;

import java.io.IOException;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;

@WebFilter("/")
public class ContentSecuirtyPolicyFilter implements Filter {
    public void  init(FilterConfig config) throws ServletException {
        System.out.println("Test init");
    }
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        System.out.println("sign up page is loaded");
        chain.doFilter(request, response);
    }

}
