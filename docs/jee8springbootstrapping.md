Bootstrapping a Spring Framework application involves setting up the necessary configurations and initializing the application context to make it ready for use. The process can vary slightly between web applications and standard Java applications.

### Bootstrapping a Spring Web Application:

1. **Web Application Context Initialization:**
   - In a Spring web application, the `DispatcherServlet` is often the entry point. The `DispatcherServlet` initializes its own `WebApplicationContext` during application startup. This can be achieved through configuration in the web.xml file or using annotations with the help of Spring's `AbstractAnnotationConfigDispatcherServletInitializer` or `AbstractDispatcherServletInitializer`.

   Example (web.xml):
   ```xml
   <servlet>
       <servlet-name>dispatcher</servlet-name>
       <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
       <init-param>
           <param-name>contextConfigLocation</param-name>
           <param-value>/WEB-INF/applicationContext.xml</param-value>
       </init-param>
       <load-on-startup>1</load-on-startup>
   </servlet>

   <servlet-mapping>
       <servlet-name>dispatcher</servlet-name>
       <url-pattern>/</url-pattern>
   </servlet-mapping>
   ```

2. **WebApplicationContext Configuration:**
   - The `WebApplicationContext` configuration typically involves specifying components like controllers, view resolvers, and other web-related beans.

   Example (Java Config):
   ```java
   @Configuration
   @EnableWebMvc
   @ComponentScan("com.example.web")
   public class WebConfig implements WebMvcConfigurer {
       // Web context configuration
   }
   ```

### Bootstrapping a Standard Spring Application:

1. **ApplicationContext Initialization:**
   - For standard Java applications, the application context can be initialized using classes like `AnnotationConfigApplicationContext` or `GenericApplicationContext`. You can specify the configuration classes or XML files containing bean definitions.

   Example (Java Config):
   ```java
   public class MainApplication {
       public static void main(String[] args) {
           AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
           // Use the context for further operations
       }
   }
   ```

   Example (XML Config):
   ```java
   public class MainApplication {
       public static void main(String[] args) {
           ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
           // Use the context for further operations
       }
   }
   ```

2. **ApplicationContext Configuration:**
   - In both cases (Java or XML configuration), you need to define the configuration class or XML file that specifies the beans and their dependencies.

   Example (Java Config):
   ```java
   @Configuration
   @ComponentScan("com.example")
   public class AppConfig {
       // Application context configuration
   }
   ```

   Example (XML Config - applicationContext.xml):
   ```xml
   <beans xmlns="http://www.springframework.org/schema/beans"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

       <!-- Bean definitions go here -->

   </beans>
   ```

In summary, bootstrapping a Spring application involves initializing the appropriate context (either `WebApplicationContext` for web applications or `ApplicationContext` for standard Java applications) and configuring it with the necessary components and dependencies. The choice between web and standard application context depends on the type of application being developed.