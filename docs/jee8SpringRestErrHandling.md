# Handling Error Conditions in REST Web Services

When developing RESTful web services, it's crucial to provide meaningful error responses to your clients. Proper error handling not only improves the user experience but also ensures that clients can effectively handle various error scenarios. In this section, we'll explore modern techniques for managing error conditions in Spring REST services.

### Changing HTTP Response Codes

One of the key aspects of error handling in RESTful services is ensuring that the correct HTTP status codes are returned for different error conditions. For instance, if a client requests a Charity Registration that does not exist, the appropriate response should be an HTTP 404 Not Found, rather than a generic 500 Internal Server Error.

You can achieve this by using the `@ResponseStatus` annotation in your Spring controllers. Here’s an example:

```java
@ResponseStatus(HttpStatus.NOT_FOUND)
public class CharityNotFoundException extends RuntimeException {
    public CharityNotFoundException(String message) {
        super(message);
    }
}
```

Then, in your controller method:

```java
@GetMapping("/charities/{id}")
public Charity getCharity(@PathVariable Long id) {
    return charityRepository.findById(id)
        .orElseThrow(() -> new CharityNotFoundException("Charity not found with id " + id));
}
```

This approach ensures that when a Charity Registration is not found, Spring automatically returns a 404 status code to the client, rather than a 500 error. This provides a more accurate response, helping clients understand what went wrong.

### Declaring Exception Handlers

In addition to setting response codes, you may want to centralize your exception handling logic. Spring provides the `@ExceptionHandler` annotation, which allows you to define custom exception handling methods within your controller.

Here’s an example of using `@ExceptionHandler`:

```java
@RestController
public class CharityController {

    @GetMapping("/charities/{id}")
    public Charity getCharity(@PathVariable Long id) {
        return charityRepository.findById(id)
            .orElseThrow(() -> new CharityNotFoundException("Charity not found with id " + id));
    }

    @ExceptionHandler(CharityNotFoundException.class)
    public ResponseEntity<String> handleCharityNotFound(CharityNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}
```

In this example, when a `CharityNotFoundException` is thrown, the `handleCharityNotFound` method catches it and returns a 404 status along with a custom error message. This allows you to manage error responses in a clean and centralized manner.
By using these techniques, you can ensure that your Spring REST services provide meaningful and appropriate error responses, enhancing the overall reliability and usability of your API.

---

### Using the Controller Advice Pattern


The `@ControllerAdvice` annotation in Spring is used to define a global exception handler or a global data binder across multiple controllers. When you add `@ControllerAdvice` to a class, that class becomes a Spring-managed component, meaning it is automatically detected and registered as a bean in the Spring application context.

### What does `@ControllerAdvice` do?

1. **Global Exception Handling**: 
   - Classes annotated with `@ControllerAdvice` can contain methods annotated with `@ExceptionHandler`, which are used to handle exceptions thrown by controllers across the entire application. This allows you to centralize exception handling logic in one place rather than having it scattered across multiple controllers.

2. **Global Model Attributes**: 
   - You can use methods annotated with `@ModelAttribute` in a `@ControllerAdvice` class to define model attributes that should be available to all controllers. This is useful for adding common data (like user information or navigation menus) that every controller might need.

3. **Global Data Binding**: 
   - Methods annotated with `@InitBinder` in a `@ControllerAdvice` class can be used to customize the binding of request parameters to model attributes or method parameters. This is useful for setting up custom editors or formatters that should apply across all controllers.

### Summary
When you mark a class with `@ControllerAdvice`, you are essentially telling Spring to manage that class as a bean, making its methods available to provide advice (like exception handling or adding common data) to multiple controllers. This helps in keeping your code clean, modular, and reusable, as common concerns can be handled in a single place rather than duplicated across controllers.

To prevent the instantiation of a `@ControllerAdvice` class in the Root ApplicationContext, you can use the `@ControllerAdvice` annotation's `basePackages`, `basePackageClasses`, or `annotations` attributes to limit its scope. This way, the advice will only be applied to controllers within the specified packages, package classes, or those annotated with specific annotations.

### Here’s how you can do it:

1. **Using `basePackages`**:
   - Specify the packages where the advice should be applied. The `@ControllerAdvice` will not be instantiated in the Root ApplicationContext if those packages are not scanned there.
   ```java
   @ControllerAdvice(basePackages = "com.example.specificpackage")
   public class MyControllerAdvice {
       // Advice methods here
   }
   ```

2. **Using `basePackageClasses`**:
   - Specify the classes in certain packages to define the scope of the advice. The advice will only apply to controllers within the same packages as these classes.
   ```java
   @ControllerAdvice(basePackageClasses = MyController.class)
   public class MyControllerAdvice {
       // Advice methods here
   }
   ```

3. **Using `annotations`**:
   - Apply the advice only to controllers that are annotated with specific annotations.
   ```java
   @ControllerAdvice(annotations = RestController.class)
   public class MyControllerAdvice {
       // Advice methods here
   }
   ```

### Why does this work?

Spring's ApplicationContext is divided into multiple contexts in some configurations, such as when using a parent (Root) context and a child context (like a WebApplicationContext). By specifying the scope of `@ControllerAdvice`, you ensure that the class is only instantiated in the appropriate child context and not in the Root ApplicationContext.

This technique is useful when you want to avoid loading specific beans in the root context, which might lead to unnecessary resource usage or potential conflicts in a large application.

### Benefits of Creating a Custom REST Stereotype Annotation:

1. **Encapsulation of Common Annotations**:
   - By creating a custom annotation, you can encapsulate multiple commonly used annotations, such as `@RestController`, `@RequestMapping`, and others. This reduces the repetition of boilerplate code across your controllers.

   ```java
   @Target(ElementType.TYPE)
   @Retention(RetentionPolicy.RUNTIME)
   @RestController
   @RequestMapping("/api/v1")
   public @interface ApiControllerV1 {
   }
   ```

   In this example, `@ApiControllerV1` automatically includes both `@RestController` and a specific base `@RequestMapping`. Any controller annotated with `@ApiControllerV1` will inherit these behaviors.

2. **Improved Code Consistency**:
   - When you create a custom stereotype annotation, you enforce consistent application of certain configurations across your controllers. This ensures that all REST endpoints adhere to the same base path, versioning scheme, or security settings, reducing the likelihood of configuration errors.

3. **Enhanced Readability and Maintainability**:
   - Custom annotations can make your code more readable and maintainable by conveying the purpose of a controller more clearly. Instead of a generic `@RestController`, a custom annotation like `@PublicApiController` or `@AdminApiController` immediately signals the role and scope of the controller.

   ```java
   @Target(ElementType.TYPE)
   @Retention(RetentionPolicy.RUNTIME)
   @RestController
   @RequestMapping("/admin")
   public @interface AdminApiController {
   }
   ```

   This makes it easier for developers to understand the structure and intent of your API just by looking at the annotations.

4. **Ease of Refactoring**:
   - If you need to change common settings across multiple controllers (e.g., a base path or version number), you only need to update your custom annotation rather than manually updating every controller.

5. **Centralized Configuration**:
   - Custom annotations can be a place to centralize configuration or logic that might otherwise be scattered across multiple controllers. For example, you might include specific exception handling, logging, or security configurations within the custom annotation.

### Example: Creating a Custom REST Stereotype Annotation

Here’s how you might create a custom annotation for versioned API controllers:

```java
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@RestController
@RequestMapping("/api/v2")
public @interface ApiControllerV2 {
}
```

Now, you can use this custom annotation in your controllers:

```java
@ApiControllerV2
public class MyNewApiController {
    @GetMapping("/resource")
    public ResponseEntity<String> getResource() {
        return ResponseEntity.ok("Resource data");
    }
}
```

### Conclusion

Creating your own specific REST stereotype annotation is a powerful way to simplify and standardize your codebase. It helps in maintaining consistency, reducing boilerplate code, and making your controllers more self-explanatory. This approach is particularly beneficial in large projects or when you have a well-defined API versioning or security strategy.

You can extend your custom REST stereotype annotation to include error handlers and exception handlers by combining `@ControllerAdvice` with your custom annotation. This way, any controller that uses your custom annotation can also be automatically linked to specific error and exception handling logic.

### Steps to Add Error Handlers and Exception Handlers:

1. **Create a Custom REST Stereotype Annotation**:
   - Start by defining your custom annotation that combines `@RestController`, `@RequestMapping`, and any other common configurations.

   ```java
   @Target(ElementType.TYPE)
   @Retention(RetentionPolicy.RUNTIME)
   @RestController
   @RequestMapping("/api/v2")
   public @interface ApiControllerV2 {
   }
   ```

2. **Create a Global Error/Exception Handler Using `@ControllerAdvice`**:
   - Define a class annotated with `@ControllerAdvice` to handle exceptions and errors globally for controllers that use your custom stereotype.

   ```java
   @ControllerAdvice(annotations = ApiControllerV2.class)
   public class ApiExceptionHandler {

       @ExceptionHandler(Exception.class)
       public ResponseEntity<String> handleGenericException(Exception ex) {
           // Handle generic exceptions
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body("An error occurred: " + ex.getMessage());
       }

       @ExceptionHandler(SpecificException.class)
       public ResponseEntity<String> handleSpecificException(SpecificException ex) {
           // Handle a specific exception
           return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body("Specific error: " + ex.getMessage());
       }

       @ExceptionHandler(AnotherSpecificException.class)
       public ResponseEntity<String> handleAnotherSpecificException(AnotherSpecificException ex) {
           // Handle another specific exception
           return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body("Not Found: " + ex.getMessage());
       }

       // Additional handlers for other exceptions or errors can be added here
   }
   ```

   This `ApiExceptionHandler` will automatically handle exceptions thrown by any controller that uses the `@ApiControllerV2` annotation.

3. **Use Your Custom Annotation in Controllers**:
   - Now, any controller annotated with `@ApiControllerV2` will inherit the common configurations and the error/exception handling logic defined in your `@ControllerAdvice`.

   ```java
   @ApiControllerV2
   public class MyNewApiController {
       @GetMapping("/resource")
       public ResponseEntity<String> getResource() {
           if (someConditionFails) {
               throw new SpecificException("A specific error occurred");
           }
           return ResponseEntity.ok("Resource data");
       }
   }
   ```

### How It Works:

- **Custom Annotation (`@ApiControllerV2`)**: This annotation bundles `@RestController` and `@RequestMapping("/api/v2")`, ensuring that all controllers using it follow a consistent API structure.
  
- **Global Exception Handling (`ApiExceptionHandler`)**: The `@ControllerAdvice` annotation, combined with `annotations = ApiControllerV2.class`, restricts the scope of this advice to controllers that are annotated with `@ApiControllerV2`. This means any exceptions thrown by these controllers will be handled by the methods in `ApiExceptionHandler`.

### Summary:

By creating a custom REST stereotype annotation and combining it with `@ControllerAdvice`, you can efficiently manage error and exception handling across your controllers. This approach centralizes your error handling logic, promotes consistency, and reduces redundancy, making your codebase more maintainable and easier to understand.

---
