package com.fixmia.rag.config;

import com.fixmia.rag.middleware.CORSFilter;
import com.fixmia.rag.util.PackageLists;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.mvc.jsp.JspMvcFeature;

//@ApplicationPath("/*")
public class AppConfig extends ResourceConfig {
    public AppConfig(){
        packages(PackageLists.CONTROLLERS.getPackageName());
        packages(PackageLists.MIDDLEWARE.getPackageName());
        packages(PackageLists.FILTER.getPackageName());
        register(CORSFilter.class);
        register(JspMvcFeature.class);
        register(DependencyBinder.class);
        property(JspMvcFeature.TEMPLATE_BASE_PATH,"/WEB-INF/Views");
        System.out.println("app is configured ");

    }
}