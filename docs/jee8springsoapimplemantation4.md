# 3. Implementing Spring Web Services for SOAP = Part 4

## Spring Web Services Introduction

---

jee8springsoapimplemantation3.md
#### Bootstrap.java

```java
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class Bootstrap implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(AppConfig.class);

        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", new DispatcherServlet(context));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/ws/*");
    }
}
```

#### AppConfig.java

```java
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@Configuration
@EnableWs
public class AppConfig extends WsConfigurerAdapter {

    @Bean
    public MessageDispatcherServlet messageDispatcherServlet() {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setTransformWsdlLocations(true);
        return servlet;
    }

    @Bean(name = "charities")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema charitiesSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("CharitiesPort");
        wsdl11Definition.setLocationUri("/ws");
        wsdl11Definition.setTargetNamespace("http://nicordesigns.com/charities");
        wsdl11Definition.setSchema(charitiesSchema);
        return wsdl11Definition;
    }

    @Bean
    public XsdSchema charitiesSchema() {
        return new SimpleXsdSchema(new ClassPathResource("charities.xsd"));
    }
}
```

### Creating a SOAP Endpoint

Create the SOAP endpoint in a class named `RegistrationEndpoint.java`:

```java
@Endpoint
public class RegistrationEndpoint {

    private static final String NAMESPACE_URI = "http://nicordesigns.com/charities";

    private final CharityRepository charityRepository;

    @Autowired
    public RegistrationEndpoint(CharityRepository charityRepository) {
        this.charityRepository = charityRepository;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "registrationRequest")
    @ResponsePayload
    public RegistrationResponse registerCharity(@RequestPayload RegistrationRequest request) {
        RegistrationResponse response = new RegistrationResponse();
        response.setStatus(charityRepository.registerCharity(request.getCharityRegistration()));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteRegistration")
    @ResponsePayload
    public DeleteResponse deleteCharity(@RequestPayload DeleteRegistration request) {
        DeleteResponse response = new DeleteResponse();
        response.setStatus(charityRepository.deleteCharity(request.getCharityId()));
        return response;
    }
}
```

### References

1. [Spring Web Services Documentation](https://docs.spring.io/spring-ws/docs/current/reference/)
2. [Spring Framework Reference Documentation](https://docs.spring.io/spring-framework/docs/current/reference/html/web.html)
3. [Jakarta EE Documentation](https://jakarta.ee/specifications/)

---
