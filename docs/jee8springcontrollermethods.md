### Part 2: Specifying Controller Method Parameters

6. **Specifying Controller Method Parameters:**
   - Controller methods can accept various parameters like `@RequestParam`, `@PathVariable`, etc., to extract data from the request.
   - Example: `@GetMapping("/example/{id}") public String handleRequest(@PathVariable("id") Long id)`.

7. **Standard Servlet Types:**
   - Spring supports the use of standard servlet types as method parameters, like `HttpServletRequest` and `HttpServletResponse`.
   - Example: `public String handleRequest(HttpServletRequest request, HttpServletResponse response)`.

8. **Annotated Request Properties:**
   - You can use annotations like `@RequestHeader`, `@CookieValue`, etc., to inject specific parts of the request into the method parameters.
   - Example: `public String handleRequest(@RequestHeader("User-Agent") String userAgent)`.

