In a Spring web application, the `DispatcherServlet` plays a crucial role in handling and dispatching incoming requests. Let's explore how `DispatcherServlets` relate to the Spring ApplicationContext and context hierarchy.

### DispatcherServlet:

1. **Role:**
   - The `DispatcherServlet` is the front controller in a Spring web application. It receives incoming HTTP requests and dispatches them to the appropriate controllers for processing.

2. **Configuration:**
   - Each `DispatcherServlet` is associated with its own `WebApplicationContext`, which is a specialized extension of the general ApplicationContext and is responsible for managing web components such as controllers, view resolvers, and handler mappings.

3. **Initialization:**
   - The `DispatcherServlet` initializes its `WebApplicationContext` during its startup. This context is separate from the root application context, creating a clear separation between the web layer and the rest of the application.

### ApplicationContext and Context Hierarchy:

1. **Root ApplicationContext:**
   - In a Spring web application, there is often a root `ApplicationContext` that manages the core application components such as services, data access objects, and business logic. This context is loaded through the ContextLoaderListener or a similar mechanism.

2. **WebApplicationContext:**
   - Each `DispatcherServlet` has its own `WebApplicationContext`, which is a child of the root application context. The web context contains components related to the web layer, such as controllers, view resolvers, and handler mappings.

3. **Hierarchical Relationship:**
   - The root `ApplicationContext` serves as the parent context for all `WebApplicationContexts`. This creates a hierarchical relationship where the root context provides beans that are common across all servlet contexts, and each servlet context can define its specific beans, overriding or extending the ones in the root.

4. **Sharing Beans:**
   - Beans defined in the root context can be accessed by all `DispatcherServlets`, promoting the reuse of common components. This is especially useful for services and business logic that are shared across different parts of the application.

### Example Configuration:

```java
// Root application context configuration (loaded by ContextLoaderListener)
@Configuration
@ComponentScan(basePackages = "com.nicordesigns")
public class RootConfig {
    // Root context configuration
}

// Web application context configuration (loaded by DispatcherServlet)
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.nicordesigns.web")
public class WebConfig implements WebMvcConfigurer {
    // Web context configuration
}
```

In the above example, `RootConfig` represents the configuration for the root application context, managing general application components. `WebConfig` represents the configuration for the `WebApplicationContext` associated with a `DispatcherServlet`, handling web-related components.

In summary, the `DispatcherServlet` and the ApplicationContext work together to manage the web layer of a Spring application. The context hierarchy allows for the segregation of concerns between the core application components and the web-specific components, promoting modularity and maintainability.