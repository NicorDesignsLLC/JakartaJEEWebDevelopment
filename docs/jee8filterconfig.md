# Creating, Declaring, and Mapping Filters in Web Development

Filters are essential components in web development that allow you to intercept and modify requests and responses, providing a powerful way to enhance the functionality and security of web applications. In this document, we will explore how to create, declare, and map filters in various ways.

## Creating Filters

To create a filter, you need to implement the [Jakarta Filter](https://jakarta.ee/specifications/platform/8/apidocs/javax/servlet/filter) interface. The interface defines three crucial methods:

- `init`: Initialization of the filter.
- `destroy`: Cleanup when the filter is no longer needed.
- `doFilter`: The core method where you code the filter's behavior.

In most cases, you will use the [HttpFilter](https://jakarta.ee/specifications/platform/8/apidocs/javax/servlet/http/httpfilter), available since the Servlet 4.0 definition. It extends the `GenericFilter` and simplifies filter development.

## Understanding the Filter Chain

Filters operate in a sequence known as the "Filter Chain." This chain determines how each filter processes requests. 
You can visualize this concept through a [Filter Chain Example](https://github.com/NicorDesignsLLC/JakartaJEEWebDevelopment/tree/jee8web-filters/charity-registration) which are mapped to our "How to Order Your Filters" slide. 

The Filter Chain passes requests from filter to filter until all filters have processed the request, ultimately reaching the servlet. If this chain is interrupted, the request may not reach its destination.

## Mapping Filters

### Mapping to URL Patterns and Servlet Names

Just like servlets, filters can be mapped to specific URL patterns or servlet names, ensuring they intercept relevant requests. You can configure filters to handle multiple URL patterns or servlets, and vice versa.

### Mapping to Different Request Dispatcher Types

Filters can also be mapped to different request dispatcher types, depending on the nature of the request. These types include:
- Error Requests: For handling errors and custom JSP error pages.
- Forward Requests: Internal requests to forward to different resources (e.g., `<jsp:forward>`).
- Include Requests: Internal requests related to the original request (e.g., `<jsp:include>`).
- Normal Requests: Requests directed to the specific application running in the container (e.g., Tomcat 9).

Additionally, there is the [Async Request Handler](https://jakarta.ee/specifications/platform/9/apidocs/jakarta/servlet/asynccontext) for handling asynchronous requests.

### Using Different Configuration Approaches

Filters can be configured using various approaches:

#### Using the Deployment Descriptor (web.xml)

Inside the `web.xml` deployment descriptor, you can define filters and their mappings. Here's an example:

```xml
<!-- Filter Ordering -->
	<filter>
        <filter-name>filterA</filter-name>
        <filter-class>com.nicordesigns.filters.FilterA</filter-class>
    </filter>

    <filter-mapping>
        <filter-name>filterA</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>

    <filter>
        <filter-name>filterB</filter-name>
        <filter-class>com.nicordesigns.filters.FilterB</filter-class>
    </filter>

    <filter-mapping>
        <filter-name>filterB</filter-name>
        <url-pattern>/servletTwo</url-pattern>
        <url-pattern>/servletThree</url-pattern>
    </filter-mapping>

    <filter>
        <filter-name>filterC</filter-name>
        <filter-class>com.nicordesigns.filters.FilterC</filter-class>
    </filter>

    <filter-mapping>
        <filter-name>filterC</filter-name>
        <url-pattern>/servletTwo</url-pattern>
    </filter-mapping>
```

#### Using Annotations

You can also use annotations, such as `@WebFilter`, to configure filters with attributes like filter name, URL patterns, servlet names, and dispatcher types:

```java
@WebFilter(
    filterName = "myFilter",
    urlPatterns = {"/foo", "/bar/**"},
    servletNames = {"myServlet"},
    dispatcherTypes = {DispatcherType.REQUEST, DispatcherType.ASYNC}
)
public class MyFilter implements Filter
```

However, note that using annotations may limit your ability to set the order of filters on a filter chain.

#### Programmatic Configuration of Filters

For dynamic configuration, you can programmatically register and map filters using the `ServletContext` and `ServletContextListener`. Here's an example:

```java
@WebListener
public class Configurator implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent event) {
        ServletContext context = event.getServletContext();
        FilterRegistration.Dynamic registration = context.addFilter("testFilter", new TestFilter());
        registration.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST, DispatcherType.ASYNC),
            false, "/foo", "/bar/*");
        registration.addMappingForServletNames(EnumSet.of(DispatcherType.REQUEST, DispatcherType.ASYNC),
            false, "myServlet");
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) { }
}
```

In this example, the filter is registered and mapped programmatically, allowing fine-grained control over filter configuration.

Understanding how to create, declare, and map filters is crucial for optimizing and securing web applications. Filters provide a flexible mechanism to intercept and process requests, making them a valuable tool in modern web development.