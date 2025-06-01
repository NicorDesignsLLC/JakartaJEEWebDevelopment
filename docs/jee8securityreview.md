## **Securing Legacy Java Systems with Spring Security**

### **Course Description**

This course is designed specifically for Java developers who are tasked with maintaining or modernizing legacy applications built with Jakarta EE 8 and Spring 5. As enterprise applications evolve, so do the threats they face. Spring Security offers a robust, customizable, and production-proven framework for implementing both basic and advanced security features with minimal overhead.

Through a series of practical examples and real-world scenarios, you’ll gain a hands-on understanding of how to secure web applications, RESTful APIs, and critical business services. Whether you're integrating with OAuth 2.0 providers, implementing JWT-based stateless sessions, or managing user roles and permissions, this course will equip you with the knowledge needed to safeguard your applications.

No prior experience with Spring Security is required—this course provides foundational knowledge and builds up to more advanced integration strategies suitable for real enterprise use.

---

### **You Will Learn To:**

* Understand the core architecture and filters behind Spring Security
* Implement authentication mechanisms, including form login, basic auth, and token-based login
* Apply authorization rules using roles, permissions, and expression-based access control
* Secure REST endpoints using OAuth 2.0 and JSON Web Tokens (JWT)
* Integrate Spring Security seamlessly with existing Jakarta EE 8 components
* Compare alternative security frameworks like Apache Shiro and Keycloak—and understand when Spring Security is the better fit
* Protect legacy systems while preparing for a possible migration to Spring Boot or other modern stacks

---

### **Who Should Take This Course:**

* Java developers working with legacy Jakarta EE or Spring MVC applications
* Teams responsible for updating old authentication/authorization code
* Architects and engineers planning a transition from monoliths to secure microservices
* Developers looking to integrate with third-party identity providers using OAuth 2.0

---

### **Technologies Covered:**

* **Spring 5 Framework** – The foundational programming model for enterprise Java development
* **Jakarta EE 8** – Key APIs used in legacy enterprise systems including Servlets, JAX-RS, and JSP
* **OpenJDK 11** – LTS version of Java, still widely used in production environments
* **Spring Security** – Main focus of the course
* **OAuth 2.0 & JWT** – For secure, stateless authentication in modern REST APIs

---

## **Core Security Concepts**

* **Authentication: Verifying User Identity**
  Spring Security handles authentication by comparing submitted credentials (username/password, token, etc.) against a configured identity store (e.g., database, LDAP, or external provider). You’ll learn how to plug in custom authentication providers and work with `AuthenticationManager`.

* **Authorization: Granting or Denying Access**
  Authorization rules determine what an authenticated user is allowed to do. You’ll implement role-based and attribute-based access control using annotations (`@PreAuthorize`, `@Secured`) and expression-based rules.

* **Security Filters: The Filter Chain That Processes Requests**
  Spring Security uses a powerful chain-of-responsibility pattern to process incoming HTTP requests. You’ll learn how filters like `UsernamePasswordAuthenticationFilter`, `BasicAuthenticationFilter`, and `ExceptionTranslationFilter` work together to secure each endpoint.

---

## **Project Setup Checklist**

1. **Add Spring Security Dependencies**

   * Use `spring-boot-starter-security` or manually include Spring Security artifacts in legacy projects.
   * Be mindful of transitive dependencies that may conflict with older Jakarta EE libraries.

2. **Configure `SecurityFilterChain` (Java Config)**

   * Replace deprecated XML and `WebSecurityConfigurerAdapter` with a bean-based approach using `@Bean public SecurityFilterChain`.
   * Configure HTTP security rules, form login, and custom filters inside this method.

3. **Disable Deprecated `WebSecurityConfigurerAdapter`**

   * Since Spring Security 5.7+, this class is considered legacy. You’ll learn how to migrate to the more declarative, functional-style configuration approach.

4. **Set Up Basic Authentication for Initial Testing**

   * Start simple: hardcode an in-memory user for rapid feedback.
   * Validate the setup by protecting a test controller and accessing it with credentials.

5. **Pro Tip: Use Spring Initializr to Bootstrap Quickly**

   * Visit [start.spring.io](https://start.spring.io) to scaffold your project with the correct dependencies, structure, and Java version.
   * Select “Spring Web” and “Spring Security” for a minimal secure web stack.
