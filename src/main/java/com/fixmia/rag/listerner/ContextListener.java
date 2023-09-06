package com.fixmia.rag.listerner;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class ContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent event){
        ServletContext context = event.getServletContext();
        context.setAttribute("BASE_URL",context.getContextPath()+"/");
        context.setAttribute("SCRIPTS",context.getContextPath()+"/scripts/");
        System.out.println(context.getAttribute("SCRIPTS").toString());;
    }
}
