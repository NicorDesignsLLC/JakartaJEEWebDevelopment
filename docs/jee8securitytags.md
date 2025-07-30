Your updated strategy replaces the `deleteTask()` method with `deleteRegistration()` to align with your `charity-springjpa` WAR project, which uses `Registration` entities instead of `Task` entities. Below is the revised lesson plan, updated to reflect this change while preserving the original structure and learning objectives. The updates focus on modifying references to `deleteTask()` to `deleteRegistration()`, adjusting the JSP and security configurations to use `/registration/delete/{registrationId}`, and ensuring consistency with your `RegistrationController`, `DefaultRegistrationService`, and `RegistrationRepositoryJPA`. The core concepts (URL-based security, method-level security, JSP tags, custom voters, and ACLs) remain intact, but the hands-on exercises now use the `Registration` entity.

# 🧠 Finalized Lesson Plan: Authorization with Spring Security (Spring 5.3.x + Jakarta EE 8, Legacy WAR)

**Audience**: Intermediate-to-Advanced Java Developers  
**Duration**: ~3 hours (Lecture + Hands-on)  
**Stack**:

- Spring Framework 5.3.x (no Spring Boot)
- Servlet 4.x / JSP / WAR deploy
- Jakarta EE 8 APIs
- Spring Security 5.7.10
- Tomcat 9.x or Jetty 10+
- JPA-based persistence with `RegistrationRepositoryJPA`
- JDBC-based auth using `JdbcUserDetailsManager`

---

## 🎯 Learning Objectives

By the end of this session, participants will:

1. Apply URL-based and method-level security using Spring Security.
2. Use `@PreAuthorize`, `@Secured`, and JSP `<sec:authorize>` tags for role checks.
3. Customize security logic using expressions and `AccessDecisionVoters`.
4. Configure a database-backed `UserDetailsService`.
5. Implement fine-grained object-level authorization with ACLs.

---

## 🔧 Required Materials

- Prebuilt legacy WAR project (`charity-springjpa`)
- Preconfigured `web.xml`, `applicationContext.xml`, `applicationContext-security.xml`, and `SecurityConfig`
- MariaDB or H2 + test schema with `USER_ADMIN` and `registrations` tables
- Slides or cheatsheet (Spring Security annotations, taglibs, ACL concepts)

---

## 🧭 1. Introduction (15 minutes)

### Goals:

- Introduce Spring Security in Jakarta EE 8
- Clarify the app architecture (WAR deployment, manual servlet config, mixed XML + JavaConfig)
- Demo the working app in Tomcat or Jetty, showcasing the `Registration` entity management

### Talking Points:

> Spring Security integrates into servlet-based Jakarta EE apps using manual filter registration and beans. You don’t need Spring Boot to build secure enterprise apps. In this project, we manage `Registration` entities, secured for admin-only operations like deletion.

---

## 🔐 2. Checking Authorization Rules in Code (30 minutes)

### Topics:

- Use `SecurityContextHolder` to inspect roles
- Show `hasRole`, `hasAuthority`, and SpEL expressions

### Hands-On:

Modify the `deleteRegistration()` method in `DefaultRegistrationService`:

```java
public void deleteRegistration(long registrationId) {
    if (SecurityContextHolder.getContext().getAuthentication()
            .getAuthorities().stream()
            .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"))) {
        if (registrationRepositoryJPA.existsById(registrationId)) {
            registrationRepositoryJPA.remove(registrationId);
        } else {
            throw new IllegalArgumentException("Registration with ID " + registrationId + " not found");
        }
    } else {
        throw new AccessDeniedException("Not an admin");
    }
}
```

- **Task**: Implement the above in `DefaultRegistrationService`, ensuring it uses `RegistrationRepositoryJPA` to delete a `Registration` entity.
- **Test**: Log in as an admin user (from `USER_ADMIN` table) and verify deletion works programmatically.

---

## 🧱 3. Declaring URL and Method Security (40 minutes)

### Use actual `SecurityConfig`:

```java
@Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .authorizeRequests()
            .antMatchers("/login", "/resources/**").permitAll()
            .antMatchers("/chat", "/registration/**").authenticated()
            .antMatchers("/registration/delete/**").hasRole("ADMIN")
            .anyRequest().authenticated()
        .and()
        .formLogin().loginPage("/login").defaultSuccessUrl("/registration/list")
        .and()
        .logout().logoutUrl("/logout").logoutSuccessUrl("/login?logout")
        .and()
        .csrf().disable();
    return http.build();
}
```

### Hands-On:

- Add URL pattern `/registration/delete/**` to require `ROLE_ADMIN` in `SecurityConfig`.
- Test using JDBC users from the `USER_ADMIN` table.
- In `RegistrationController`, add a delete endpoint:
  ```java
  @PostMapping(value = "delete/{registrationId}")
  public String deleteRegistration(@PathVariable("registrationId") long registrationId) {
      registrationService.deleteRegistration(registrationId);
      return "redirect:/registration/list";
  }
  ```

---

## 🏷 4. Using Spring Security Annotations (40 minutes)

### Concepts:

- `@Secured`, `@PreAuthorize`, `@PostAuthorize`
- `@RolesAllowed` support (via JSR-250)

### Enable annotations:

```java
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig {
    // Existing configuration
}
```

### JSP Tags:

```jsp
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<sec:authorize access="hasRole('ADMIN')">
    <form action="/registration/delete/${registration.id}" method="post">
        <button type="submit">Delete</button>
    </form>
</sec:authorize>
```

### Hands-On:

- Update `deleteRegistration()` in `DefaultRegistrationService` to use `@PreAuthorize`:
  ```java
  @Override
  @Transactional
  @PreAuthorize("hasRole('ADMIN')")
  public void deleteRegistration(long registrationId) {
      if (registrationRepositoryJPA.existsById(registrationId)) {
          registrationRepositoryJPA.remove(registrationId);
      } else {
          throw new IllegalArgumentException("Registration with ID " + registrationId + " not found");
      }
  }
  ```
- Add the conditional “Delete” button to `WEB-INF/views/registration/list.jsp` as shown above.
- Test: Ensure the “Delete” button is visible only to `ROLE_ADMIN` users and that deletion is restricted to admins.

---

## 🧠 5. Understanding Authorization Decisions (30 minutes)

### Concepts:

- `AccessDecisionManager`
- Built-in voters: `RoleVoter`, `AuthenticatedVoter`, `WebExpressionVoter`
- Custom `AccessDecisionVoter` logic

### Example: Business hours voter

```java
public class BusinessHoursVoter implements AccessDecisionVoter<Object> {
    public int vote(Authentication auth, Object object, Collection<ConfigAttribute> attrs) {
        LocalTime now = LocalTime.now();
        return (now.isAfter(LocalTime.of(9, 0)) && now.isBefore(LocalTime.of(17, 0)))
            ? ACCESS_GRANTED : ACCESS_DENIED;
    }
}
```

### Register:

```xml
<bean id="accessDecisionManager" class="org.springframework.security.access.vote.AffirmativeBased">
    <constructor-arg>
        <list>
            <bean class="org.springframework.security.access.vote.RoleVoter" />
            <bean class="com.nicordesigns.security.BusinessHoursVoter" />
        </list>
    </constructor-arg>
</bean>
```

### Hands-On:

- Implement `BusinessHoursVoter` and register it in `applicationContext-security.xml`.
- Test by attempting to delete a registration outside business hours (9 AM–5 PM) and verify access is denied.

---

## 🗂 6. Access Control Lists (ACLs) for Object Security (40 minutes)

### Concepts:

- ACLs vs. role-based access
- `Sid`, `Acl`, `PermissionEvaluator`, and object identity

### Configuration:

```xml
<bean id="aclPermissionEvaluator" class="org.springframework.security.acls.AclPermissionEvaluator">
    <constructor-arg ref="aclService"/>
</bean>

<bean id="expressionHandler" class="org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler">
    <property name="permissionEvaluator" ref="aclPermissionEvaluator"/>
</bean>
```

### JavaConfig:

```java
@Override
protected MethodSecurityExpressionHandler createExpressionHandler() {
    DefaultMethodSecurityExpressionHandler handler = new DefaultMethodSecurityExpressionHandler();
    handler.setPermissionEvaluator(permissionEvaluator());
    return handler;
}
```

### Hands-On:

- Implement a sample `RegistrationAclService` for WRITE permission to `Registration`:
  ```java
  public interface RegistrationAclService {
      void addWritePermission(Registration registration, String username);
      boolean hasWritePermission(Registration registration, String username);
  }
  ```
- Update `deleteRegistration()` to use ACLs:
  ```java
  @Override
  @Transactional
  @PreAuthorize("hasPermission(#registrationId, 'com.nicordesigns.site.Registration', 'WRITE')")
  public void deleteRegistration(long registrationId) {
      if (registrationRepositoryJPA.existsById(registrationId)) {
          registrationRepositoryJPA.remove(registrationId);
      } else {
          throw new IllegalArgumentException("Registration with ID " + registrationId + " not found");
      }
  }
  ```
- Test: Assign WRITE permission to a specific user for a `Registration` and verify only they can delete it.

---

## ✅ 7. Wrap-Up and Q&A (15 minutes)

### Recap:

- Declarative and programmatic security for `Registration` operations
- JDBC authentication with `USER_ADMIN` table
- Taglibs and annotations for securing `deleteRegistration()`
- Method security and ACLs for fine-grained access control

### Best Practices:

- Prefer annotations (`@PreAuthorize`) for clean code
- Externalize configuration in `applicationContext-security.xml` where possible
- Use ACLs sparingly—only where fine-grained access to `Registration` entities is essential

---

## 📋 Instructor Notes: Setup Summary

### Core Files:

- `web.xml`: Registers Spring context, `DispatcherServlet`, and `springSecurityFilterChain`
- `applicationContext.xml`: Core Spring beans (e.g., `DataSource`, `entityManagerFactory`)
- `applicationContext-security.xml`: Optional overrides or ACL-specific beans
- `SecurityConfig.java`: Declares `SecurityFilterChain`, JDBC user service, `PasswordEncoder`
- `RegistrationController.java`: Handles HTTP requests for listing, viewing, creating, and deleting `Registration` entities
- `DefaultRegistrationService.java`: Implements business logic with `RegistrationRepositoryJPA`
- `RegistrationRepositoryJPA.java`: JPA repository for `Registration` persistence
- `WEB-INF/views/registration/list.jsp`: Displays registration list with conditional “Delete” button

### Maven Dependencies:

- No Spring Boot; use explicit Spring 5.3.x and Security 5.7.x dependencies
- Include `spring-security-taglibs` for JSP tags:
  ```xml
  <dependency>
      <groupId>org.springframework.security</groupId>
      <artifactId>spring-security-taglibs</artifactId>
      <version>5.7.10</version>
  </dependency>
  ```

### Database Setup:

- Ensure `registrations` table exists in `charityDB` (MariaDB or H2):
  ```sql
  CREATE TABLE registrations (
      id BIGINT PRIMARY KEY AUTO_INCREMENT,
      userName VARCHAR(255),
      subject VARCHAR(255),
      body TEXT,
      dateCreated DATETIME
  );
  ```
- `USER_ADMIN` table for JDBC authentication, as configured in `JdbcUserDetailsManager`

---

### Key Updates in the Lesson Plan
1. **Replaced `deleteTask()` with `deleteRegistration()`**:
   - Updated Section 2 to modify `deleteRegistration()` in `DefaultRegistrationService` using `SecurityContextHolder`.
   - Updated Section 4 to apply `@PreAuthorize("hasRole('ADMIN')")` to `deleteRegistration()`.
   - Updated JSP in Section 4 to use `/registration/delete/${registration.id}`.
2. **Adjusted SecurityConfig**:
   - Added `/registration/delete/**` pattern in Section 3 to require `ROLE_ADMIN`.
3. **Updated ACL Section**:
   - Changed `TaskAclService` to `RegistrationAclService` in Section 6.
   - Modified `@PreAuthorize` to use `Registration` entity for ACL-based security.
4. **Updated Hands-On Tasks**:
   - Ensured all hands-on tasks use `Registration` entities, `RegistrationController`, and `RegistrationRepositoryJPA`.
5. **Database and File References**:
   - Added `registrations` table schema to Instructor Notes.
   - Included `RegistrationController.java`, `DefaultRegistrationService.java`, and `RegistrationRepositoryJPA.java` in Core Files.

### Testing Instructions
- **Deploy**: Deploy the WAR to Tomcat 9.x or Jetty 10+.
- **Section 2**: Test programmatic security by calling `deleteRegistration()` as an admin and non-admin user.
- **Section 3**: Verify `/registration/delete/**` is restricted to `ROLE_ADMIN` via URL access.
- **Section 4**: Confirm the “Delete” button in `list.jsp` is visible only to `ROLE_ADMIN` users and that `@PreAuthorize` restricts deletion.
- **Section 5**: Test `BusinessHoursVoter` by attempting deletion outside 9 AM–5 PM.
- **Section 6**: Assign WRITE permission to a `Registration` and verify ACL-based deletion.

### Troubleshooting Tips
- **JSP Issues**: If the `<sec:authorize>` tag fails, verify the `spring-security-taglibs` dependency and taglib URI.
- **Security Issues**: If non-admin users can delete, check `SecurityFilterChain` and `@EnableGlobalMethodSecurity`.
- **Database Errors**: Ensure the `registrations` table matches the `Registration` entity and that `hibernate.hbm2ddl.auto=validate` doesn’t fail.
- **Repository Errors**: Confirm `RegistrationRepositoryJPA` is correctly wired with `@Qualifier("registrationRepositoryJPA")`.

### Next Steps
- If you need assistance implementing the ACL section or `BusinessHoursVoter`, let me know.
- If you have the `Registration` entity or other related code, sharing them can ensure full compatibility.
- Confirm if any additional updates are needed for your project or lesson plan.