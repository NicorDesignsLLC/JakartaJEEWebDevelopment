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

        AnnotationConfigWebApplicationContext servletContext =
                new AnnotationConfigWebApplicationContext();
        servletContext.register(ServletContextConfiguration.class);
        ServletRegistration.Dynamic webDispatcher = container.addServlet("springDispatcher", new DispatcherServlet(servletContext));
        webDispatcher.setLoadOnStartup(1);  // Set load-on-startup only here
        webDispatcher.setMultipartConfig(new MultipartConfigElement(
        	    null, 20_971_520L, 41_943_040L, 512_000
        	));
        webDispatcher.addMapping("/");  // Only once
        
        AnnotationConfigWebApplicationContext restContext = new AnnotationConfigWebApplicationContext();
        restContext.register(RestServletContextConfiguration.class);
        DispatcherServlet restServlet = new DispatcherServlet(restContext);
        restServlet.setDispatchOptionsRequest(true);
        ServletRegistration.Dynamic restDispatcher = container.addServlet("springRestDispatcher", restServlet);
        restDispatcher.setLoadOnStartup(2); // Set for REST Dispatcher
        restDispatcher.addMapping("/services/Rest/*");
        
        AnnotationConfigWebApplicationContext soapContext =
                new AnnotationConfigWebApplicationContext();
        soapContext.register(SoapServletContextConfiguration.class);
        MessageDispatcherServlet soapServlet =
                new MessageDispatcherServlet(soapContext);
        soapServlet.setTransformWsdlLocations(true);
        ServletRegistration.Dynamic dispatcher = container.addServlet("springSoapDispatcher", soapServlet); // Declare dispatcher
        dispatcher.setLoadOnStartup(3);
        dispatcher.addMapping("/services/Soap/*");
       

        // Logging filter
        FilterRegistration.Dynamic loggingFilter = container.addFilter("loggingFilter", new LoggingFilter());
        loggingFilter.addMappingForUrlPatterns(null, false, "/*");

        // Authentication filter
        FilterRegistration.Dynamic authenticationFilter = container.addFilter("authenticationFilter", new AuthenticationFilter());
        authenticationFilter.addMappingForUrlPatterns(null, false, "/registration", "/registration/*", "/chat", "/chat/*", "/session", "/session/*");
    }

}
