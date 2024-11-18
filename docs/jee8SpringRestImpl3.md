# Segregating Controllers with Stereotype Annotations

When working with Spring MVC, the `@Controller` annotation serves two key purposes. First, as an `@Component`, it marks the controller as a Spring bean, making it eligible for instantiation and dependency injection by the Spring Framework. Second, within the context of Spring MVC, it designates beans to be scanned for `@RequestMapping` annotations, which map incoming requests to specific controller methods.

However, challenges arise when we need to prevent our REST controllers from being scanned by the web DispatcherServlet and vice versa. To address this, we can use the following strategies:

1. **Use Different Package Names**: This approach involves organizing your controllers into separate packages, allowing you to specify which packages each DispatcherServlet should scan. This prevents unwanted controllers from being picked up.

2. **Spring Meta Annotations**: A more flexible method is to use Spring Meta Annotations. By creating custom annotations that combine `@Controller` with other specific settings, you can control which controllers are scanned by the respective DispatcherServlets.

#### Example 1: Meta Annotation for Web Controllers

```java
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Controller
@RequestMapping
public @interface WebController {
    @AliasFor(annotation = Controller.class)
    String value() default "";
}
```

In this example, `@WebController` is a custom annotation that combines `@Controller` and `@RequestMapping`. Apply this annotation to your web controllers to ensure they are scanned by the web DispatcherServlet only.

#### Example 2: Meta Annotation for REST Controllers

```java
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@RestController
@RequestMapping("/api")
public @interface RestApiController {
    @AliasFor(annotation = RestController.class)
    String value() default "";
}
```

Here, `@RestApiController` is a custom annotation that combines `@RestController` and `@RequestMapping("/api")`. Apply this annotation to your REST controllers to ensure they are scanned by the REST DispatcherServlet and that their base URL is prefixed with `/api`.

By using these meta annotations, we can effectively segregate our web and REST controllers, maintaining a clean and organized application structure.

---

### Creating Separate Web and REST Application Contexts

Once you've applied your new custom annotations to your REST controllers and endpoints, the next step is to update the ServletContextConfiguration class. Specifically, you'll configure it to scan only for the `@WebController` annotation, preventing cross-context confusion. To make this distinction clear, we'll rename the `ServletContextConfiguration` to `WebServletContextConfiguration`. Additionally, we'll create a new `RestServletContextConfiguration` similar to our previous `SOAPServletContextConfiguration` class.

#### Example: WebServletContextConfiguration

```java
@Configuration
@ComponentScan(basePackageClasses = WebController.class)
public class WebServletContextConfiguration {
    // Existing configuration remains intact
}
```

#### Example: RestServletContextConfiguration

```java
@Configuration
@ComponentScan(basePackageClasses = RestApiController.class)
public class RestServletContextConfiguration {
    // Configures REST-specific settings
    @Bean
    public MappingJackson2HttpMessageConverter jsonConverter() {
        return new MappingJackson2HttpMessageConverter();
    }

    @Bean
    public MarshallingHttpMessageConverter xmlConverter() {
        return new MarshallingHttpMessageConverter();
    }

    @Override
    protected void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.favorPathExtension(false)
                  .favorParameter(false)
                  .ignoreAcceptHeader(false)
                  .defaultContentType(MediaType.APPLICATION_JSON)
                  .mediaType("json", MediaType.APPLICATION_JSON)
                  .mediaType("xml", MediaType.APPLICATION_XML);
    }

    @Bean
    public LocaleResolver localeResolver() {
        return new AcceptHeaderLocaleResolver();
    }
}
```

In the `RestServletContextConfiguration` class, we focus on marshalling and unmarshalling JSON and XML, and configure content negotiation to prioritize the `Accept` header. Since REST is a stateless protocol, we also use the `AcceptHeaderLocaleResolver`.

Finally, we need to update the Bootstrap class. We'll rename the existing `DispatcherServletDeclaration` and add a new `DispatcherServletConfiguration` to handle REST requests.

#### Example: Bootstrap Class

```java
public class ApplicationInitializer implements WebApplicationInitializer {
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        // Web DispatcherServlet
        AnnotationConfigWebApplicationContext webContext = new AnnotationConfigWebApplicationContext();
        webContext.register(WebServletContextConfiguration.class);
        ServletRegistration.Dynamic webDispatcher = servletContext.addServlet("webDispatcher", new DispatcherServlet(webContext));
        webDispatcher.setLoadOnStartup(1);
        webDispatcher.addMapping("/");

        // REST DispatcherServlet
        AnnotationConfigWebApplicationContext restContext = new AnnotationConfigWebApplicationContext();
        restContext.register(RestServletContextConfiguration.class);
        ServletRegistration.Dynamic restDispatcher = servletContext.addServlet("restDispatcher", new DispatcherServlet(restContext));
        restDispatcher.setLoadOnStartup(2);
        restDispatcher.addMapping("/api/*");
        restDispatcher.setAsyncSupported(true);
        restDispatcher.setInitParameter("dispatchOptionsRequest", "true");
    }
}
```

Notice that the REST servlet does not handle multipart requests, and the `dispatchOptionsRequest` property is set to `true` to support the discovery of REST services.

---
