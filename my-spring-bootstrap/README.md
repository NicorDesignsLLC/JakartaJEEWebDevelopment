### Prerequisites:
- [Eclipse IDE](https://www.eclipse.org/downloads/)
- [Maven](https://maven.apache.org/download.cgi) installed on your system.


### Step 1: Set Up a Maven Project in Eclipse
1. Open Eclipse IDE.
2. Go to `File` -> `New` -> `Other...`.
3. Select `Maven` -> `Maven Project` and click `Next`.
4. Choose `Create a simple project` and click `Next`.
5. Enter the `Group ID` and `Artifact ID` for your project. Click `Finish`. 

		  <parent>
		    <groupId>pom-root-level</groupId>
		    <artifactId>JakartaJEEWebDevelopment</artifactId>
		    <version>1.0.0-SNAPSHOT</version>
		  </parent>
		  
		  <artifactId>my-spring-bootstrap</artifactId>
		  <packaging>war</packaging>
		  <name>my-spring-mvc</name>
		  <description>my-spring-bootstrap</description>

		  
### Step 2: Create a Bootstrap class implementing WebApplicationInitializer

Create a class named `Bootstrap` that implements `WebApplicationInitializer`. Override the `onStartup` method to initialize the root context and servlet context using `AnnotationConfigWebApplicationContext`.

```java
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

public class Bootstrap implements WebApplicationInitializer {

    @Override
    public void onStartup(javax.servlet.ServletContext servletContext) {
        // Initialize root context
        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.register(RootConfig.class); // Replace RootConfig with your actual configuration class
        servletContext.addListener(new ContextLoaderListener(rootContext));

        // Initialize servlet context
        AnnotationConfigWebApplicationContext servletContextConfig = new AnnotationConfigWebApplicationContext();
        servletContextConfig.register(ServletContextConfig.class); // Replace ServletContextConfig with your actual configuration class

        // Create DispatcherServlet and register it
        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", new DispatcherServlet(servletContextConfig));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");
    }
}
```

### Step 3: Create Java Configuration classes

Create two Java configuration classes, one for the root context (`RootConfig`) and one for the servlet context (`ServletContextConfig`).

Replace the content of these classes with the corresponding XML configuration. You can use the `@Configuration` annotation for Java-based configuration.

### Step 4: Update Maven Dependencies

Ensure that your `pom.xml` includes the necessary dependencies for Spring MVC and Servlet API. You can keep these dependencies in your `pom.xml` file.

### Step 5: Update the web.xml configuration

Remove the `web.xml` file or update it to only include the `Bootstrap` class as the initializer. Remove the configuration related to the XML files.

### Step 6: Update Controller and JSP

Your controller and JSP can remain the same. Ensure that the package names and configurations in the controller match your Java-based configuration.

### Step 7: Run and Deploy

Run the project using Maven, and deploy the generated WAR file to a servlet container.

Adjust package names, URLs, and configurations based on your preferences.

