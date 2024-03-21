# Migrating Jakarta EE 8 Servlets to Spring Controllers with `@RequestMapping`

## Prerequisites
- [Eclipse IDE](https://www.eclipse.org/downloads/)
- [Maven](https://maven.apache.org/download.cgi) installed on your system.

## Step 1: Set Up the Spring Framework in our Maven Project in Eclipse
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
	
	    <dependency>
	        <groupId>org.springframework</groupId>
	        <artifactId>spring-oxm</artifactId>
	        <version>${spring.framework.version}</version>
	        <scope>compile</scope>
	    </dependency>
	    
	    <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-websocket</artifactId>
            <version>${spring.framework.version}</version>
            <scope>compile</scope>
        </dependency>
	
	    <!-- Servlet API -->
	    <dependency>
	        <groupId>javax.servlet</groupId>
	        <artifactId>javax.servlet-api</artifactId>
	        <version>4.0.1</version>
	        <scope>provided</scope>
	    </dependency>
	</dependencies>

## Step 2: Create the Spring MVC Java based Configuration context
1. Create a com.nicordesigns.site.config package folder in the src/main directory.
2. Inside the package folder, create your Java-based Spring MVC configuration classes:

		@Configuration
		@ComponentScan(basePackages = "com.nicordesigns.site", 
		               useDefaultFilters = false, 
		               includeFilters = @ComponentScan.Filter(org.springframework.stereotype.Controller.class))
		public class RootContextConfiguration {
		}
		
		@SuppressWarnings("unused")
		public class Bootstrap implements WebApplicationInitializer {
		}
		
		@Configuration
		@EnableWebMvc
		@ComponentScan(
		    basePackages = "com.nicordesigns.site",
		    useDefaultFilters = false,
		    includeFilters = @ComponentScan.Filter(org.springframework.stereotype.Controller.class))
		public class ServletContextConfiguration implements WebMvcConfigurer {
		}
		
	
## Step 3: Convert Servlets to Spring Controllers
1. Replace Command Pattern with @RequestMapping.
2. Handle Request Parameters.
3. Refactor Switch Statements.
4. Handle Request and Response Objects.
5. Dependency Injection.
6. Update View Resolution.
## Example Controller Classes:

	@Controller
	@RequestMapping("registration")
	public class RegistrationController {
	}
	
	@Controller
	public class IndexController {
	}
	
	@Controller
	@RequestMapping("session")
	public class SessionListController {
	}

## Step 6: Run the Project
1. Right-click on the project -> Run As -> Maven Build...
2. Set clean install as Goals and click Run
## Step 7: Deploy to a Servlet Container
1. Deploy the generated WAR file (usually found in the target folder) to a servlet container like Apache Tomcat 9.
## Step 8: Access the Application
1. Once deployed, access the application at http://localhost:8080/charity-springmvc/
2. Adjust package names, URLs, and configurations based on your preferences.


