package com.nicordesigns.site.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.http.converter.xml.SourceHttpMessageConverter;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.Unmarshaller;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nicordesigns.site.config.annotation.RestEndpoint;
import com.nicordesigns.site.config.annotation.RestEndpointAdvice;

@Configuration
@EnableWebMvc
@ComponentScan(
        basePackages = "com.nicordesigns.site",
        useDefaultFilters = false,
        includeFilters = @ComponentScan.Filter({RestEndpoint.class, RestEndpointAdvice.class})
)
public class RestServletContextConfiguration implements WebMvcConfigurer {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private Marshaller marshaller;

    @Autowired
    private Unmarshaller unmarshaller;

    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        return new ObjectMapper();  // Customize your ObjectMapper here if needed
    }

    @Bean
    public MarshallingHttpMessageConverter marshallingHttpMessageConverter() {
        MarshallingHttpMessageConverter xmlConverter = new MarshallingHttpMessageConverter();
        xmlConverter.setSupportedMediaTypes(Arrays.asList(
                new MediaType("application", "xml"),
                new MediaType("text", "xml")
        ));
        xmlConverter.setMarshaller(this.marshaller);
        xmlConverter.setUnmarshaller(this.unmarshaller);
        return xmlConverter;
    }

    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
        jsonConverter.setSupportedMediaTypes(Arrays.asList(
                new MediaType("application", "json"),
                new MediaType("text", "json")
        ));
        jsonConverter.setObjectMapper(this.objectMapper);
        return jsonConverter;
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new SourceHttpMessageConverter<>());
        converters.add(marshallingHttpMessageConverter());
        converters.add(mappingJackson2HttpMessageConverter());
    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.ignoreAcceptHeader(false)
                  .defaultContentType(MediaType.APPLICATION_XML)
                  .mediaType("xml", MediaType.APPLICATION_XML)
                  .mediaType("json", MediaType.APPLICATION_JSON);
    }

    @Bean
    public LocaleResolver localeResolver() {
        return new AcceptHeaderLocaleResolver();
    }
}
