# Configuring REST using Spring MVC

### Introduction to Configuring REST Services using Spring MVC

Welcome to this lesson on configuring REST services using Spring MVC. If you've been following our series, you know that our Charity Registration Application already has a well-established web interface. But what if we want to extend this application to include RESTful web services?

In this lesson, we'll guide you through the steps to do just that. We'll start by explaining why it's not ideal to mix our existing web controllers with new REST controllers. Our current DispatcherServlet Application Context is specifically tailored for web UI components, like decorators and session listeners, which are unnecessary for REST services.

To ensure our application remains clean and efficient, we'll take the following steps:

1. **Create a Separate DispatcherServlet**: We’ll set up a dedicated DispatcherServlet exclusively for our REST controllers. This will allow our REST services to function independently from the web UI context.

2. **Segregate Web and REST Controllers**: By separating our web controllers from our REST controllers, we avoid context overlap and maintain a clearer, more organized structure.

3. **Set Up a Separate Context Configuration**: We’ll establish a distinct context configuration stack specifically for our REST controllers, optimized for the needs of RESTful services.

4. **Develop a REST Controller**: We’ll demonstrate how to create a REST controller that can handle requests from both JSON and XML clients, ensuring our service is versatile and user-friendly.

5. **Incorporate Proper Error Handling**: Robust error handling is crucial for REST services. We’ll show you how to integrate effective error handlers to ensure graceful management and response to errors.

By the end of this lesson, you’ll be equipped to configure REST services in Spring MVC in a way that’s efficient, maintainable, and clearly separated from your web UI components.


### Mapping REST Requests to Controller Methods

Mapping REST requests to controller methods in a Charity Registration Spring Web MVC application involves defining the appropriate endpoints in your controller classes and associating them with the relevant HTTP methods (GET, POST, PUT, DELETE) that correspond to CRUD operations.

### Steps to Map REST Requests to Controller Methods:

1. **Define a Controller Class**:
   - Start by creating a controller class in your Spring Web MVC application and annotate it with `@RestController` (or a custom annotation like `@ApiControllerV2` if you're using that approach).

   ```java
   @ApiControllerV2
   public class CharityRegistrationController {
       // Endpoint methods go here
   }
   ```

2. **Map HTTP GET Requests**:
   - Use `@GetMapping` to map GET requests to methods that retrieve resources. For instance, you might want to get a list of all registered charities or details of a specific charity.

   ```java
   @GetMapping("/charities")
   public ResponseEntity<List<Charity>> getAllCharities() {
       List<Charity> charities = charityService.getAllCharities();
       return ResponseEntity.ok(charities);
   }

   @GetMapping("/charities/{id}")
   public ResponseEntity<Charity> getCharityById(@PathVariable Long id) {
       Charity charity = charityService.getCharityById(id);
       return ResponseEntity.ok(charity);
   }
   ```

   - The `@GetMapping("/charities")` method maps to the `/charities` endpoint and retrieves a list of all charities, while the `@GetMapping("/charities/{id}")` method retrieves details of a specific charity using its ID.

3. **Map HTTP POST Requests**:
   - Use `@PostMapping` to map POST requests to methods that create new resources. For example, registering a new charity.

   ```java
   @PostMapping("/charities")
   public ResponseEntity<Charity> registerCharity(@RequestBody Charity charity) {
       Charity savedCharity = charityService.registerCharity(charity);
       return ResponseEntity.status(HttpStatus.CREATED).body(savedCharity);
   }
   ```

   - The `@PostMapping("/charities")` method maps to the `/charities` endpoint and allows the client to submit data for a new charity registration.

4. **Map HTTP PUT Requests**:
   - Use `@PutMapping` to map PUT requests to methods that update existing resources. This could be used to update charity details.

   ```java
   @PutMapping("/charities/{id}")
   public ResponseEntity<Charity> updateCharity(@PathVariable Long id, @RequestBody Charity charityDetails) {
       Charity updatedCharity = charityService.updateCharity(id, charityDetails);
       return ResponseEntity.ok(updatedCharity);
   }
   ```

   - The `@PutMapping("/charities/{id}")` method maps to the `/charities/{id}` endpoint and updates the charity identified by the provided ID.

5. **Map HTTP DELETE Requests**:
   - Use `@DeleteMapping` to map DELETE requests to methods that delete resources. For example, removing a charity from the registration list.

   ```java
   @DeleteMapping("/charities/{id}")
   public ResponseEntity<Void> deleteCharity(@PathVariable Long id) {
       charityService.deleteCharity(id);
       return ResponseEntity.noContent().build();
   }
   ```

   - The `@DeleteMapping("/charities/{id}")` method maps to the `/charities/{id}` endpoint and deletes the specified charity.

### Bringing It All Together:

Each method in your `CharityRegistrationController` class corresponds to a specific operation on the charity resources:

- **GET /charities**: Retrieves all registered charities.
- **GET /charities/{id}**: Retrieves details of a specific charity by its ID.
- **POST /charities**: Registers a new charity.
- **PUT /charities/{id}**: Updates an existing charity's details.
- **DELETE /charities/{id}**: Deletes a specific charity by its ID.

### Bonus Tip:

If you’re enhancing your application with additional features or videos explaining how to work with RESTful endpoints, you can draw from real-world examples. For instance, when explaining how to map requests, you could show how this relates to a user’s experience—like signing up for a course or registering an organization, much like how someone would subscribe to a YouTube series or manage content like you do with your own series. This makes the content more relatable and engaging for your audience.


By following these steps, you'll be able to configure and manage REST services in your Spring MVC application effectively, ensuring that your web and REST components are cleanly separated and optimized for performance.