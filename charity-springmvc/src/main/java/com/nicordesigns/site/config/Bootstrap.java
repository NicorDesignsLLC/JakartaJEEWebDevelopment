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
import org.springframework.ws.transport.http.MessageDispatcherServlet;

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
        AnnotationConfigWebApplicationContext servletContext = new AnnotationConfigWebApplicationContext();
        servletContext.register(ServletContextConfiguration.class);
        ServletRegistration.Dynamic dispatcher = container.addServlet("springDispatcher", new DispatcherServlet(servletContext));
        dispatcher.setLoadOnStartup(1);
        dispatcher.setMultipartConfig(new MultipartConfigElement(null, 20971520L, 41943040L, 512000));
        dispatcher.addMapping("/");

        // Message Dispatcher Servlet for SOAP services
        MessageDispatcherServlet soapServlet = new MessageDispatcherServlet(servletContext);
        soapServlet.setTransformWsdlLocations(true);
        ServletRegistration.Dynamic soapDispatcher = container.addServlet("springSoapDispatcher", soapServlet);
        soapDispatcher.setLoadOnStartup(2);
        soapDispatcher.addMapping("/services/Soap/*");

        // Logging filter
        FilterRegistration.Dynamic loggingFilter = container.addFilter("loggingFilter", new LoggingFilter());
        loggingFilter.addMappingForUrlPatterns(null, false, "/*");

        // Authentication filter
        FilterRegistration.Dynamic authenticationFilter = container.addFilter("authenticationFilter", new AuthenticationFilter());
        authenticationFilter.addMappingForUrlPatterns(null, false, "/registration", "/registration/*", "/chat", "/chat/*", "/session", "/session/*");
    }
}
