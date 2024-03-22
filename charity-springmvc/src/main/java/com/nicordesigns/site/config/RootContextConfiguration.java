package com.nicordesigns.site.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;

@Configuration
@EnableAsync(proxyTargetClass = true)
@EnableScheduling
@ComponentScan(basePackages = "com.nicordesigns.site", 
excludeFilters = @ComponentScan.Filter(Controller.class)	
               //useDefaultFilters = false, 
               //includeFilters = @ComponentScan.Filter(org.springframework.stereotype.Controller.class)
)
public class RootContextConfiguration {
    @Bean
    public ObjectMapper objectMapper()
    {
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.configure(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE,
                false);
        return mapper;
    }

    @Bean
    public Jaxb2Marshaller jaxb2Marshaller()
    {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setPackagesToScan(new String[] { "com.nicordesigns.site" });
        return marshaller;
    }
}
