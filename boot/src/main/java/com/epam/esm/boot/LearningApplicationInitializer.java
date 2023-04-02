package com.epam.esm.boot;

import com.epam.esm.boot.config.WebConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRegistration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class LearningApplicationInitializer  implements WebApplicationInitializer {
    @Override
    public void onStartup(jakarta.servlet.ServletContext servletContext) throws ServletException {
        AnnotationConfigWebApplicationContext root = new AnnotationConfigWebApplicationContext();
        root.register(WebConfig.class);
        servletContext.addListener(new ContextLoaderListener(root));
        ServletRegistration.Dynamic servlet =
                servletContext.addServlet("learning", new DispatcherServlet(root));
        servlet.setLoadOnStartup(1);
        servlet.addMapping("/*");
    }
}
