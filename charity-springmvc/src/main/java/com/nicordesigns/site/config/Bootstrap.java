package com.nicordesigns.site.config;

import javax.servlet.FilterRegistration;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import com.nicordesigns.site.filters.AuthenticationFilter;
import com.nicordesigns.site.filters.LoggingFilter;
import com.nicordesigns.site.SessionListener;

public class Bootstrap implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext container) throws ServletException {
        container.getServletRegistration("default").addMapping("/resources/**");

        // Root context
        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.register(RootContextConfiguration.class);
        container.addListener(new ContextLoaderListener(rootContext));
        container.addListener(SessionListener.class);

        // Dispatcher Servlet for web application
        AnnotationConfigWebApplicationContext webContext = new AnnotationConfigWebApplicationContext();
        webContext.register(ServletContextConfiguration.class);
        ServletRegistration.Dynamic webDispatcher = container.addServlet("springDispatcher", new DispatcherServlet(webContext));
        webDispatcher.addMapping("/");  // Only once
        webDispatcher.setLoadOnStartup(1);  // Set load-on-startup only here
        
        // REST Dispatcher Servlet
        AnnotationConfigWebApplicationContext restContext = new AnnotationConfigWebApplicationContext();
        restContext.register(RestServletContextConfiguration.class);
        DispatcherServlet restServlet = new DispatcherServlet(restContext);
        restServlet.setDispatchOptionsRequest(true);
        ServletRegistration.Dynamic restDispatcher = container.addServlet("springRestDispatcher", restServlet);
        restDispatcher.setLoadOnStartup(2); // Set for REST Dispatcher
        restDispatcher.addMapping("/services/Rest/*");
        
        // Multipart config - only needed if both servlets support file uploads
        webDispatcher.setMultipartConfig(new MultipartConfigElement(null, 20971520L, 41943040L, 512000));

        // Logging filter
        FilterRegistration.Dynamic loggingFilter = container.addFilter("loggingFilter", new LoggingFilter());
        loggingFilter.addMappingForUrlPatterns(null, false, "/*");

        // Authentication filter
        FilterRegistration.Dynamic authenticationFilter = container.addFilter("authenticationFilter", new AuthenticationFilter());
        authenticationFilter.addMappingForUrlPatterns(null, false, "/registration", "/registration/*", "/chat", "/chat/*", "/session", "/session/*");
    }

}
