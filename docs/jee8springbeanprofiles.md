Using Bean Definition Profiles in a Spring application allows you to define different sets of beans for different environments or scenarios. Profiles help in managing configuration variations in a flexible and modular way. Here's a detailed explanation of how to use Bean Definition Profiles in a Spring application:

1. **Define Profiles:**
   - In your Spring configuration, annotate classes or methods with `@Profile("profileName")`. This indicates that the beans defined within these components are specific to the given profile.

   ```java
   @Configuration
   @Profile("development")
   public class DevelopmentConfig {
       // Beans specific to the development profile
   }
   ```

   ```java
   @Configuration
   @Profile("production")
   public class ProductionConfig {
       // Beans specific to the production profile
   }
   ```

2. **Activate Profiles:**
   - Profiles can be activated in various ways:
     - **In XML Configuration:**
       ```xml
       <beans profile="development">
           <!-- Beans for development profile -->
       </beans>
       ```
     - **In Java Configuration:**
       ```java
       @Configuration
       @Profile("development")
       public class DevelopmentConfig {
           // Beans specific to the development profile
       }
       ```

   - You can also activate profiles programmatically in the `ApplicationContext` during application startup:

     ```java
     AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
     context.getEnvironment().setActiveProfiles("development");
     context.register(DevelopmentConfig.class, OtherConfig.class);
     context.refresh();
     ```

3. **Default Profile:**
   - You can specify a default profile using `@Profile("default")` or by not specifying any profile. Beans defined without a profile or with the default profile will be registered regardless of the active profiles.

   ```java
   @Configuration
   public class DefaultConfig {
       // Beans for the default profile
   }
   ```

4. **Properties-Based Profile Activation:**
   - Profiles can be activated based on properties. In your `application.properties` or `application.yml` file, specify the active profiles:

     ```properties
     spring.profiles.active=development
     ```

5. **Conditional Bean Creation:**
   - Use the `@ConditionalOnProfile` annotation to conditionally create beans based on the active profiles. This is particularly useful when a bean should only be created for specific profiles.

   ```java
   @Configuration
   @ConditionalOnProfile("development")
   public class ConditionalBeanConfig {
       // This bean will only be created if the "development" profile is active
   }
   ```

6. **Testing with Profiles:**
   - When testing, you can activate profiles specifically for tests. In JUnit, you can use the `@ActiveProfiles` annotation:

   ```java
   @RunWith(SpringRunner.class)
   @SpringBootTest
   @ActiveProfiles("test")
   public class MyTest {
       // Test-specific configuration for the "test" profile
   }
   ```

Using Bean Definition Profiles provides a clean and modular way to manage configuration differences across various environments or scenarios in your Spring application. This flexibility is particularly valuable for handling configuration variations in development, testing, and production environments.