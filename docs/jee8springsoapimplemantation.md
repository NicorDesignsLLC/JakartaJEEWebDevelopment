# 3. Implementing Spring Web Services for SOAP

## Spring Web Services Introduction

The topic of Spring Web Services is vast and can easily fill an entire book. In this guide, we'll provide a concise introduction and practical example to get you started. For more comprehensive information, refer to the [official Spring Web Services documentation](https://docs.spring.io/spring-ws/docs/current/reference/html).

## Required Maven Dependencies

To begin, you need to include the necessary Maven dependencies in your `pom.xml` file:

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.ws</groupId>
        <artifactId>spring-ws-core</artifactId>
        <version>3.1.1.RELEASE</version>
    </dependency>
    <dependency>
        <groupId>javax.servlet</groupId>
        <artifactId>javax.servlet-api</artifactId>
        <version>4.0.1</version>
        <scope>provided</scope>
    </dependency>
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-webmvc</artifactId>
        <version>5.3.10</version>
    </dependency>
</dependencies>
```

---

## Creating a SOAP Endpoint for Charity Registration

In this example, we will create a SOAP endpoint for registering charities in our Jakarta JEE8 Web App. For now, we will not secure our SOAP endpoint as we will cover that later.

### Writing Our Contract-First XSD and WSDL

Spring Web Services enforces a contract-first approach to development, meaning you must define your XML schema before anything else.

#### Charity Registration XML Document Example

Here is an example of a Charity Registration XML document:

```xml
<CharityRegistration>
    <CharityName>Example Charity</CharityName>
    <CharityAddress>123 Charity Lane</CharityAddress>
    <CharityPhone>123-456-7890</CharityPhone>
    <CharityEmail>info@examplecharity.org</CharityEmail>
</CharityRegistration>
```

We also need a Charity Registration Form XML document. Additionally, we want to be able to add or delete a charity registration, so we need a `registrationRequest` and a `deleteRegistration` XML element.

#### Charity Registration Form XML Document Example

```xml
<CharityRegistrationForm>
    <CharityName></CharityName>
    <CharityAddress></CharityAddress>
    <CharityPhone></CharityPhone>
    <CharityEmail></CharityEmail>
</CharityRegistrationForm>
```

#### Registration Request and Delete Registration XML Elements

```xml
<registrationRequest>
    <CharityRegistration>
        <CharityName>Example Charity</CharityName>
        <CharityAddress>123 Charity Lane</CharityAddress>
        <CharityPhone>123-456-7890</CharityPhone>
        <CharityEmail>info@examplecharity.org</CharityEmail>
    </CharityRegistration>
</registrationRequest>

<deleteRegistration>
    <CharityId>123</CharityId>
</deleteRegistration>
```

We will use an open-source tool to generate the XSD schema for us from these XML documents.

### Generating XSD from XML Using FreeFormatter.com

FreeFormatter.com provides an easy-to-use, online tool for generating an XML Schema (XSD) from an XML document. Here’s how you can use it:

1. **Visit FreeFormatter.com:**
   - Open your web browser and go to [FreeFormatter XML to XSD Generator](https://www.freeformatter.com/xsd-generator.html#before-output).

2. **Input Your XML Content:**
   - Copy and paste your XML content into the text box provided on the website.

3. **Generate the XSD:**
   - Click the "Generate XSD" button. The tool will process your XML and produce an XSD schema.

4. **Download or Copy the XSD:**
   - You can either download the generated XSD file or copy the content directly from the website.

### Example of Generated XSD

Here is an example of what the generated XSD might look like:

```xml
<xs:schema xmlns:xs="http://www.w3.org/2001/XMLSchema">
    <xs:element name="CharityRegistration">
        <xs:complexType>
            <xs:sequence>
                <xs:element name="CharityName" type="xs:string"/>
                <xs:element name="CharityAddress" type="xs:string"/>
                <xs:element name="CharityPhone" type="xs:string"/>
                <xs:element name="CharityEmail" type="xs:string"/>
            </xs:sequence>
        </xs:complexType>
    </xs:element>

    <xs:element name="CharityRegistrationForm">
        <xs:complexType>
            <xs:sequence>
                <xs:element name="CharityName" type="xs:string"/>
                <xs:element name="CharityAddress" type="xs:string"/>
                <xs:element name="CharityPhone" type="xs:string"/>
                <xs:element name="CharityEmail" type="xs:string"/>
            </xs:sequence>
        </xs:complexType>
    </xs:element>

    <xs:element name="registrationRequest">
        <xs:complexType>
            <xs:sequence>
                <xs:element name="CharityRegistration" type="CharityRegistration"/>
            </xs:sequence>
        </xs:complexType>
    </xs:element>

    <xs:element name="deleteRegistration">
        <xs:complexType>
            <xs:sequence>
                <xs:element name="CharityId" type="xs:string"/>
            </xs:sequence>
        </xs:complexType>
    </xs:element>
</xs:schema>
```

With the XSD file generated, you can now proceed to create your WSDL and implement your SOAP endpoint in your Jakarta JEE8 Web App.

---

### Adding the SOAP Dispatcher Servlet Configuration

To configure the SOAP dispatcher servlet in a Jakarta JEE8 environment, we need to set up the `Bootstrap` class and related configuration.

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
