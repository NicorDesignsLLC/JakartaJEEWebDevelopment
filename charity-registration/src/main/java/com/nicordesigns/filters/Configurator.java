package com.nicordesigns.filters;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class Configurator implements ServletContextListener {
	@Override
	public void contextInitialized(ServletContextEvent event) {

		ServletContext context = event.getServletContext();

		FilterRegistration.Dynamic registration = context.addFilter("requestLogFilter", new RequestLogFilter());
		registration.addMappingForUrlPatterns(null, false, "/*");

		registration = context.addFilter("compressionFilter", new CompressionFilter());
		registration.setAsyncSupported(true);
		registration.addMappingForUrlPatterns(null, false, "/*");

//		FilterRegistration.Dynamic registration = context.addFilter("authenticationFilter", new AuthenticationFilter());
//		registration.setAsyncSupported(true);
//		registration.addMappingForUrlPatterns(null, false, "/registrations", "/sessions");

	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
	}
}
