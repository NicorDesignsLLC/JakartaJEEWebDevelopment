# 🧠 Finalized Lesson Plan: Authorization with Spring Security (Spring 5.3.x + Jakarta EE 8, Legacy WAR)

**Audience**: Intermediate-to-Advanced Java Developers
**Duration**: \~3 hours (Lecture + Hands-on)
**Stack**:

* Spring Framework 5.3.x (no Spring Boot)
* Servlet 4.x / JSP / WAR deploy
* Jakarta EE 8 APIs
* Spring Security 5.7.10
* Tomcat 9.x or Jetty 10+
* JDBC-based auth using `JdbcUserDetailsManager`

---

## 🎯 Learning Objectives

By the end of this session, participants will:

1. Apply URL-based and method-level security using Spring Security.
2. Use `@PreAuthorize`, `@Secured`, and JSP `<sec:authorize>` tags for role checks.
3. Customize security logic using expressions and AccessDecisionVoters.
4. Configure a database-backed `UserDetailsService`.
5. Implement fine-grained object-level authorization with ACLs.

---

## 🔧 Required Materials

* Prebuilt legacy WAR project (`charity-springjpa`)
* Preconfigured `web.xml`, `applicationContext.xml`, `applicationContext-security.xml`, and `SecurityConfig`
* MariaDB or H2 + test schema with a `USER_ADMIN` table
* Slides or cheatsheet (Spring Security annotations, taglibs, ACL concepts)

---

## 🧭 1. Introduction (15 minutes)

### Goals:

* Introduce Spring Security in Jakarta EE 8
* Clarify the app architecture (WAR deployment, manual servlet config, mixed XML + JavaConfig)
* Demo the working app in Tomcat or Jetty

### Talking Points:

> Spring Security integrates into servlet-based Jakarta EE apps using manual filter registration and beans. You don’t need Spring Boot to build secure enterprise apps.

---

## 🔐 2. Checking Authorization Rules in Code (30 minutes)

### Topics:

* Use `SecurityContextHolder` to inspect roles
* Show `hasRole`, `hasAuthority`, and SpEL expressions

### Hands-On:

Modify the `deleteTask()` method:

```java
public void deleteTask(Long taskId) {
    if (SecurityContextHolder.getContext().getAuthentication()
            .getAuthorities().stream()
            .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"))) {
        // Delete logic
    } else {
        throw new AccessDeniedException("Not an admin");
    }
}
```

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

* Add URL patterns for `/admin/**` that require `ROLE_ADMIN`.
* Test using JDBC users from the `USER_ADMIN` table.

---

## 🏷 4. Using Spring Security Annotations (40 minutes)

### Concepts:

* `@Secured`, `@PreAuthorize`, `@PostAuthorize`
* `@RolesAllowed` support (via JSR-250)

### Enable annotations:

```java
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
```

### JSP Tags:

```jsp
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<sec:authorize access="hasRole('ADMIN')">
    <a href="/admin/deleteTask">Delete</a>
</sec:authorize>
```

### Hands-On:

* Add `@PreAuthorize("hasRole('ADMIN')")` to `deleteTask()`
* Add conditional button to JSP

---

## 🧠 5. Understanding Authorization Decisions (30 minutes)

### Concepts:

* `AccessDecisionManager`
* Built-in voters: `RoleVoter`, `AuthenticatedVoter`, `WebExpressionVoter`
* Custom `AccessDecisionVoter` logic

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

---

## 🗂 6. Access Control Lists (ACLs) for Object Security (40 minutes)

### Concepts:

* ACLs vs. role-based access
* `Sid`, `Acl`, `PermissionEvaluator`, and object identity

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

* Implement a sample `TaskAclService` for WRITE permission to `Task`
* Use `@PreAuthorize("hasPermission(#task, 'WRITE')")`

---

## ✅ 7. Wrap-Up and Q\&A (15 minutes)

### Recap:

* Declarative and programmatic security
* JDBC authentication
* Taglibs + annotations
* Method security and ACLs

### Best Practices:

* Prefer annotations for clean code
* Externalize configuration where possible
* Use ACLs sparingly — only where fine-grained access is essential

---

## 📋 Instructor Notes: Setup Summary

### Core Files:

* `web.xml`: Registers Spring context, DispatcherServlet, and `springSecurityFilterChain`
* `applicationContext.xml`: Core Spring beans
* `applicationContext-security.xml`: Optional overrides or ACL-specific beans
* `SecurityConfig.java`: Declares `SecurityFilterChain`, JDBC user service, `PasswordEncoder`

### Maven Dependencies:

No Spring Boot; use explicit Spring 5.3.x and Security 5.7.x dependencies as defined in your actual `pom.xml`.

