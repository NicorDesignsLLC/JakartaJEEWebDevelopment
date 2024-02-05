Certainly! Here's the formatted README document:

## Prerequisites:

- [Eclipse IDE](https://www.eclipse.org/downloads/)
- [Maven](https://maven.apache.org/download.cgi) installed on your system.

## Step 1: Set Up a Maven Project in Eclipse

1. Open Eclipse IDE.
2. Go to `File` -> `New` -> `Other...`.
3. Select `Maven` -> `Maven Project` and click `Next`.
4. Choose `Create a simple project` and click `Next`.
5. Enter the `Group ID` and `Artifact ID` for your project. Click `Finish`. 

```xml
<parent>
    <groupId>pom-root-level</groupId>
    <artifactId>JakartaJEEWebDevelopment</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</parent>

<artifactId>spring-bean-profiles</artifactId>
<packaging>war</packaging>
<name>spring-bean-profiles</name>
<description>spring-bean-profiles</description>
```

## Step 2: Add Spring MVC Dependencies

1. Open the `pom.xml` file in the project.
2. Add the following dependencies for Spring MVC:

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-webmvc</artifactId>
        <version>${spring.framework.version}</version>
        <scope>compile</scope>
    </dependency>
</dependencies>
```

Adjust versions based on the latest available versions.

## Step 3(a): Create the Spring MVC Configuration context

1. Create a `webapp/WEB-INF` folder in the `src/main` directory.
2. Inside the `WEB-INF` folder, create a `rootContext.xml` file for your Spring MVC configuration:

```xml
<!-- Content of rootContext.xml -->
...<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:context="http://www.springframework.org/schema/context"
	xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-4.0.xsd
						http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-4.0.xsd">
	
	
	<context:annotation-config/>
	
	<context:component-scan
		base-package="com.nicordesigns">
		<context:exclude-filter type="annotation"
			expression="org.springframework.stereotype.Controller"/>
	</context:component-scan>
	
	<!-- Import the properties file based on the active profile -->
	<beans profile="dev">
		<bean
			class="org.springframework.beans.factory.config.PropertyPlaceholderConfigurer">
			<property name="location"
				value="classpath:application-dev.properties"/>
		</bean>
		<bean id="greetingService"
			class="com.nicordesigns.GreetingServiceImpl">
			<property name="greetingMessage" value="${greeting.message}"/>
		</bean>
	</beans>
	<beans profile="prod">
		<bean
			class="org.springframework.beans.factory.config.PropertyPlaceholderConfigurer">
			<property name="location"
				value="classpath:application-prod.properties"/>
		</bean>
		<bean id="greetingService"
			class="com.nicordesigns.GreetingServiceImpl">
			<property name="greetingMessage" value="${greeting.message}"/>
		</bean>
	</beans>
</beans>
```

3. Create a `webapp/WEB-INF/views` folder for your JSP views.

## Step 3(b): Update the web.xml configuration file

```xml
<!-- Content of web.xml -->

...<?xml version="1.0" encoding="UTF-8"?>
<!-- src/main/webapp/WEB-INF/web.xml -->
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
         version="4.0">

    <display-name>Spring Application</display-name>

    <context-param>
        <param-name>contextConfigLocation</param-name>
        <param-value>/WEB-INF/rootContext.xml</param-value>
    </context-param>
  
    <context-param>
        <param-name>spring.profiles.active</param-name>
        <param-value>dev</param-value>
    </context-param>
    
    <listener>
        <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
    </listener>

    <servlet-mapping>
        <servlet-name>default</servlet-name>
        <url-pattern>/resource/*</url-pattern>
    </servlet-mapping>

    <servlet>
        <servlet-name>springDispatcher</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>/WEB-INF/servletContext.xml</param-value>
        </init-param>
        <load-on-startup>1</load-on-startup>
    </servlet>
    <servlet-mapping>
        <servlet-name>springDispatcher</servlet-name>
        <url-pattern>/</url-pattern>
    </servlet-mapping>

    <jsp-config>
        <jsp-property-group>
            <url-pattern>*.jsp</url-pattern>
            <url-pattern>*.jspf</url-pattern>
            <page-encoding>UTF-8</page-encoding>
            <scripting-invalid>true</scripting-invalid>
            <include-prelude>/WEB-INF/jsp/base.jspf</include-prelude>
            <trim-directive-whitespaces>true</trim-directive-whitespaces>
            <default-content-type>text/html</default-content-type>
        </jsp-property-group>
    </jsp-config>

    <session-config>
        <session-timeout>30</session-timeout>
        <cookie-config>
            <http-only>true</http-only>
        </cookie-config>
        <tracking-mode>COOKIE</tracking-mode>
    </session-config>

    <distributable />

</web-app>

```

## Step 3(c): Create the Spring Servlet Context Configuration (servletContext.xml)

```xml
<!-- Content of servletContext.xml -->
...<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
            http://www.springframework.org/schema/beans/spring-beans-4.0.xsd
            http://www.springframework.org/schema/context
            http://www.springframework.org/schema/context/spring-context-4.0.xsd
            http://www.springframework.org/schema/mvc
            http://www.springframework.org/schema/mvc/spring-mvc-4.0.xsd">

    <mvc:annotation-driven />

    <context:annotation-config />
    <context:component-scan base-package="com.nicordesigns" use-default-filters="false">
        <context:include-filter type="annotation"
                expression="org.springframework.stereotype.Controller" />
                
	</context:component-scan>
    
    

</beans>
```

## Step 4: Create a Controller

1. Create a package (e.g., `com.nicordesigns`) in `src/main/java`.
2. Inside the package, create a "Hello World" controller class:

```java
package com.nicordesigns;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    private final GreetingService greetingService;

    // Constructor injection
    @Autowired
    public HelloController(GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    @ResponseBody
    @RequestMapping("/")
    public String helloWorld() {
        return this.greetingService.getGreeting("Started");
    }

    @ResponseBody
    @RequestMapping(value = "/", params = {"name"})
    public String helloName(@RequestParam("name") String name) {
        return this.greetingService.getGreeting(name);
    }
}
```

## Step 5: Run the Project

1. Right-click on the project -> `Run As` -> `Maven Build...`.
2. Set `clean install` as Goals and click `Run`.

## Step 6: Deploy to a Servlet Container

1. Deploy the generated WAR file (usually found in the `target` folder) to a servlet container like Apache Tomcat 9.

## Step 7: Access the Application

Once deployed, access the application at `http://localhost:8080/spring-bean-profiles/`.
```