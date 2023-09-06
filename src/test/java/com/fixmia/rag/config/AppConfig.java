package com.fixmia.rag.config;

import com.fixmia.rag.util.PackageLists;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.mvc.jsp.JspMvcFeature;

public class AppConfig extends ResourceConfig {
    public AppConfig(){
        packages(PackageLists.CONTROLLERS.getPackageName());
        packages(PackageLists.MIDDLEWARE.getPackageName());
        register(JspMvcFeature.class);
        property(JspMvcFeature.TEMPLATE_BASE_PATH,"/WEB-INF/Views");

        System.out.println("app is configured ");

    }
}
class Test{
    public static void main(String[] args) {
    }
}