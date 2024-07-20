# 3. Implementing Spring Web Services for SOAP - Maven Archetype

## Using a Spring Web Services Archetype

In this section, you will create a boilerplate Spring Web Services project in **Eclipse** using the Spring Web Services Maven Archetype.

### Steps:

1. **Project Setup**
   - **Step 1: Open Eclipse**
     - Launch Eclipse IDE on your computer.

   - **Step 2: Create a New Maven Project**
     - Go to `File > New > Project`.
     - Select `Maven Project` from the list and click `Next`.

   - **Step 3: Select Project Location**
     - Choose the workspace location for your project or accept the default location and click `Next`.

   - **Step 4: Select an Archetype**
     - In the Archetype selection window, click `Add Archetype`.
     - Enter the following details:
       - **Group Id:** `org.springframework.ws`
       - **Artifact Id:** `spring-ws-archetype`
       - **Version:** `2.4.0` (or the latest version available)
     - Click `OK`, then select the added archetype and click `Next`.

   - **Step 5: Define Project Coordinates**
     - Enter the following details:
       - **Group Id:** `com.nicordesigns`
       - **Artifact Id:** `spring-ws-charityregistration`
       - **Version:** `1.0-SNAPSHOT`
     - Click `Finish`.

2. **Configuration**
   - **Step 1: Open Spring Configuration File**
     - Navigate to `src/main/resources` and open the Spring configuration file 		(`applicationContext.xml` or in our case `web.xml`  and `spring-ws-servlet.xml` ).

   - **Step 2: Enable WSDL Generation**
     - Add the necessary configurations to generate the SOAP WSDL file automatically. This typically involves defining beans for `DefaultWsdl11Definition` and `XsdSchema`.

3. **Service Layer Development**
   - **Step 1: Create Service Interface**
     - In the `src/main/java` directory, create a new package and a Java Interface for 		your service. Interface name to align with generated WSDL wsdl:portType

   - **Step 2: Implement Service Interface**
     - Create an implementation class for the service interface.

4. **SOAP Endpoint Implementation**
   - **Step 1: Define Endpoint Class**
     - Create a new class for your SOAP endpoint and annotate it with `@Endpoint`.

   - **Step 2: Map Requests to Methods**
     - Use `@PayloadRoot`, `@RequestPayload`, and `@ResponsePayload` annotations to map incoming SOAP requests to appropriate methods.


### References

1. [Spring Web Services Documentation](https://docs.spring.io/spring-ws/docs/current/reference/)
2. [Spring Framework Reference Documentation](https://docs.spring.io/spring-framework/docs/current/reference/html/web.html)
3. [Jakarta EE Documentation](https://jakarta.ee/specifications/)
