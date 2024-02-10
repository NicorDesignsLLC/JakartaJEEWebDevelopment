### Part 1: Request Parameter Restrictions

1. **@RequestMapping:**
   - `@RequestMapping` is an annotation in Spring used to map web requests to specific handler methods in a controller.
   - It can be applied at the class level to indicate the base URI for all methods in the controller and/or at the method level to narrow down the mapping.

2. **Using @RequestMapping attributes to narrow RequestMapping:**
   - Attributes like `method`, `params`, `headers`, and `consumes` can be used with `@RequestMapping` to further narrow down the mapping.
   - For example, using `method = RequestMethod.GET` ensures that the method only handles GET requests.

3. **URL Restrictions:**
   - You can use `value` or `path` attributes in `@RequestMapping` to specify the URL patterns that the method should handle.
   - For example, `@RequestMapping("/example")` will map to requests with the URL pattern "/example".

4. **Request Header Restrictions:**
   - The `headers` attribute in `@RequestMapping` can be used to specify the required request headers for the method to be invoked.
   - Example: `@RequestMapping(value = "/example", headers = "key=value")`.

5. **Content Type Restriction:**
   - The `consumes` attribute in `@RequestMapping` restricts the content types that the method can consume.
   - Example: `@RequestMapping(value = "/example", consumes = "application/json")`.


In the context of Jakarta EE 8, Spring can be integrated with Jakarta EE technologies to leverage features like CDI (Contexts and Dependency Injection) and JPA (Java Persistence API) alongside Spring's functionality. The core concepts of request mapping and handling remain consistent, and the second part delves into the specifics of specifying controller method parameters for enhanced data extraction from the incoming requests.