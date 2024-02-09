1. **@RequestMapping:**
   - `@RequestMapping` is an annotation in Spring that is used to map web requests to specific handler methods in a controller.
   - It can be applied at the class level to indicate the base URI for all methods in the controller, and/or at the method level to narrow down the mapping.

2. **Using @RequestMapping attributes to narrow RequestMapping:**
   - Attributes like `method`, `params`, and `headers` can be used with `@RequestMapping` to further narrow down the mapping.
   - For example, using `method = RequestMethod.GET` ensures that the method only handles GET requests.

3. **URL Restrictions:**
   - You can use `value` or `path` attributes in `@RequestMapping` to specify the URL patterns that the method should handle.
   - For example, `@RequestMapping("/example")` will map to requests with the URL pattern "/example".

4. **Request Header Restrictions:**
   - `headers` attribute in `@RequestMapping` can be used to specify the required request headers for the method to be invoked.
   - Example: `@RequestMapping(value = "/example", headers = "key=value")`.

5. **Content Type Restriction:**
   - `consumes` attribute in `@RequestMapping` restricts the content types that the method can consume.
   - Example: `@RequestMapping(value = "/example", consumes = "application/json")`.

6. **Specifying Controller Method Parameters:**
   - Controller methods can accept various parameters like `@RequestParam`, `@PathVariable`, etc., to extract data from the request.
   - Example: `@GetMapping("/example/{id}") public String handleRequest(@PathVariable("id") Long id)`.

7. **Standard Servlet Types:**
   - Spring supports the use of standard servlet types as method parameters, like `HttpServletRequest` and `HttpServletResponse`.
   - Example: `public String handleRequest(HttpServletRequest request, HttpServletResponse response)`.

8. **Annotated Request Properties:**
   - You can use annotations like `@RequestHeader`, `@CookieValue`, etc., to inject specific parts of the request into the method parameters.
   - Example: `public String handleRequest(@RequestHeader("User-Agent") String userAgent)`.

In the context of Jakarta EE 8, Spring can be integrated with Jakarta EE technologies to leverage features like CDI (Contexts and Dependency Injection) and JPA (Java Persistence API) alongside Spring's functionality. The core concepts of request mapping and handling remain consistent, but we may also benefit from Jakarta EE specifications and features where applicable.