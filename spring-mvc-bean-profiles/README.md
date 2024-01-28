### Prerequisites:
- [Eclipse IDE](https://www.eclipse.org/downloads/)
- [Maven](https://maven.apache.org/download.cgi) installed on your system.

### Step 1: Set Up a Maven Project in Eclipse
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
    
    <artifactId>spring-mvc-bean-profiles</artifactId>
    <packaging>war</packaging>
    <name>spring-mvc-bean-profiles</name>
    <description>spring-mvc-bean-profiles</description>
    ```

### Step 2: Add Spring MVC Dependencies
1. Open the `pom.xml` file in the project.
2. Add the following dependencies for Spring MVC:

    ```xml
    <dependencies>
        <!-- Spring MVC -->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-webmvc</artifactId>
            <version>5.3.12.RELEASE</version> <!-- Use the latest version -->
        </dependency>
        
        <!-- Servlet API -->
        <dependency>
            <groupId>javax.servlet</groupId>
            <artifactId>javax.servlet-api</artifactId>
            <version>4.0.1</version> <!-- Use the version compatible with your servlet container -->
            <scope>provided</scope>
        </dependency>
    </dependencies>
    ```

    Adjust versions based on the latest available versions.

### Step 3(a): Create the Spring MVC Configuration context
1. Create a `webapp/WEB-INF` folder in the `src/main` directory.
2. Inside the `WEB-INF` folder, create a `spring-config.xml` file for your Spring MVC configuration:

    ```xml
    <!-- spring-config.xml -->
    <beans xmlns="http://www.springframework.org/schema/beans"
           xmlns:mvc="http://www.springframework.org/schema/mvc"
           xmlns:context="http://www.springframework.org/schema/context"
           xsi:schemaLocation="http://www.springframework.org/schema/beans
             http://www.springframework.org/schema/beans/spring-beans.xsd
             http://www.springframework.org/schema/mvc
             http://www.springframework.org/schema/mvc/spring-mvc.xsd
             http://www.springframework.org/schema/context
             http://www.springframework.org/schema/context/spring-context.xsd"
           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">

       <!-- Component scanning for beans -->
       <context:component-scan base-package="com.nicordesigns" />

       <!-- Enable Spring MVC -->
       <mvc:annotation-driven />

       <!-- View resolver for JSP views -->
       <bean id="viewResolver" class="org.springframework.web.servlet.view.InternalResourceViewResolver">
           <property name="prefix" value="/WEB-INF/views/" />
           <property name="suffix" value=".jsp" />
       </bean>
       
    </beans>
    ```

3. Create a `webapp/WEB-INF/views` folder for your JSP views.

### Step 3(b): Update the web.xml configuration file to include the Spring and Spring Servlet Context Configuration as well as the dispatcher servlet definition

    ```xml
    <!-- src/main/webapp/WEB-INF/web.xml -->
    <web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
             version="4.0">
    
        <context-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>/WEB-INF/spring-config.xml</param-value>
        </context-param>
    
        <listener>
            <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
        </listener>
        
        <servlet>
            <servlet-name>dispatcher</servlet-name>
            <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
            <init-param>
                <param-name>contextConfigLocation</param-name>
                <param-value>/WEB-INF/servletContext.xml</param-value>
            </init-param>
            <load-on-startup>1</load-on-startup>
        </servlet>
    
        <servlet-mapping>
            <servlet-name>dispatcher</servlet-name>
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

### Step 3(c): Create the Spring Servlet Context Configuration

    ```xml
    <!-- src/main/webapp/WEB-INF/servletContext.xml -->
    <web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
             version="4.0">
    
        <context-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>/WEB-INF/spring-config.xml</param-value>
        </context-param>
    
        <listener>
            <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
        </listener>
        
        <servlet>
            <servlet-name>dispatcher</servlet-name>
            <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
            <init-param>
                <param-name>contextConfigLocation</param-name>
                <param-value>/WEB-INF/servletContext.xml</param-value>
            </init-param>
            <load-on-startup>1</load-on-startup>
        </servlet>
    
        <servlet-mapping>
            <servlet-name>dispatcher</servlet-name>
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

### Step 4: Create a Controller
1. Create a package (e.g., `com.nicordesigns`) in `src/main/java`.
2. Inside the package, create a "Hello World" controller class:

    ```java
    package com.nicordesigns.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

	@Controller
	public class GreetingController {
	
	    @Value("${greeting.message}")
	    private String greetingMessage;
	
	    @GetMapping("/greet")
	    public String greet(Model model) {
	        model.addAttribute("greeting", greetingMessage);
	        return "greeting";
	    }
	}
    ```

### Step 5: Create a JSP View
1. Inside `webapp/WEB-INF/views`, create a `greeting.jsp` file:

    ```jsp
	    <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
		<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
		<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
		<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
		<!DOCTYPE html>
		<html>
		<head>
		    <title>Greeting</title>
		</head>
		<body>
		    <h1>${greeting}</h1>
		</body>
		</html>
```

### Step 6: Run the Project
1. Right-click on the project -> `Run As` -> `Maven Build...`.
2. Set `clean install` as Goals and click `Run`.

### Step 7: Deploy to a Servlet Container
1. Deploy the generated WAR file (usually found in the `target` folder) to a servlet container like Apache Tomcat 9 in our case.

### Step 8: Access the Application
1. Once deployed, access the application at `http://localhost:8080/spring-mvc-bean-profiles/hello`.
