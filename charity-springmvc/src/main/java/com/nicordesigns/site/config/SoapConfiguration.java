package com.nicordesigns.site.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@EnableWs
@Configuration
public class SoapConfiguration extends WsConfigurerAdapter {

    @Bean
    public Jaxb2Marshaller jaxb2Marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("com.nicordesigns");
        return marshaller;
    }

    @Bean(name = "charity")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema charitySchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("CharityRegistrationService");
        wsdl11Definition.setLocationUri("/charityRegistrationService");
        wsdl11Definition.setTargetNamespace("http://www.nicordesigns.com/charity");
        wsdl11Definition.setSchema(charitySchema);
        return wsdl11Definition;
    }

    @Bean
    public XsdSchema charitySchema() {
        return new SimpleXsdSchema(new ClassPathResource("xsd/CharityRegistration.xsd"));
    }

}
