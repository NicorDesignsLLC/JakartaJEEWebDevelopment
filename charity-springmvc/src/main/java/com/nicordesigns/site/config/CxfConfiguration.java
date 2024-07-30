package com.nicordesigns.site.config;
import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.nicordesigns.site.ws.CharityRegistrationImpl;


@Configuration
public class CxfConfiguration {

    @Bean
    public EndpointImpl endpoint(Bus bus) {
        EndpointImpl endpoint = new EndpointImpl(bus, new CharityRegistrationImpl());
        endpoint.publish("/CharityRegistration");
        return endpoint;
    }
}
