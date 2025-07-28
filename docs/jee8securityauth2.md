# 🧠 Lesson 2: Securing a Legacy Jakarta EE Web App with Spring Security Authorization (Spring 5.3.x)

### 🚨 Builds on: Lesson 1 — Authenticating Users in a Legacy Spring Web Application

---

## 🎯 Lesson Objectives

By the end of this lesson, participants will:

✅ Replace custom authentication filters with Spring Security’s standard form login

✅ Enable URL and method-level authorization using Java config and annotations

✅ Apply Spring Security tags to JSP views

✅ Understand how Spring Security makes authorization decisions

✅ Configure ACLs for fine-grained access (optional advanced section)

---

## 🧑‍💻 Assumptions

The application currently uses:

* Custom login via `AuthenticationController`
* Servlet `AuthenticationFilter`
* Session-bound `UserAdminPrincipal`
* JSP-based UI with Spring form tags
* No CSRF, no concurrent session protection

We will **replace these with Spring Security equivalents**.

---

## 🧭 1. Introduction (15 minutes)

### 🧩 Bridge from Lesson 1:

* Recap: Custom filter validates session principal and redirects to `/login`
* Transition: Migrate to `SecurityFilterChain` with Spring-managed login/logout/session policies

### ✅ Key Shifts:

| Before (Lesson 1)                    | Now (Lesson 2)                        |
| ------------------------------------ | ------------------------------------- |
| `AuthenticationFilter`               | `springSecurityFilterChain` bean      |
| Session-managed `UserAdminPrincipal` | Spring Security Authentication object |
| Manual login controller              | Spring Security form login flow       |

---

## 🔐 2. Spring Security Authentication Flow (20 minutes)

### 🔧 Configuration: `SecurityConfig.java`

```java
@Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .authorizeRequests()
            .antMatchers("/login", "/resources/**").permitAll()
            .antMatchers("/registration/**", "/chat/**", "/session/**").authenticated()
            .anyRequest().authenticated()
        .and()
        .formLogin()
            .loginPage("/login")
            .defaultSuccessUrl("/registration/list", true)
            .failureUrl("/login?error")
        .and()
        .logout()
            .logoutUrl("/logout")
            .logoutSuccessUrl("/login?logout")
        .and()
        .csrf()
        .and()
        .sessionManagement().maximumSessions(1);

    return http.build();
}
```

### 🧠 Notes:

* No more need for `AuthenticationFilter` servlet class
* Spring handles session, CSRF, and concurrent logins automatically
* Principal is accessed via `SecurityContextHolder`

---

## 🧱 3. URL-Based Authorization Rules (20 minutes)

### Goal:

Replace hardcoded role checks and servlet filter protection with declarative config.

### Exercise:

Modify `SecurityConfig` to restrict:

```java
http
    .authorizeRequests()
        .antMatchers("/admin/**").hasRole("ADMIN")
        .antMatchers("/registration/**").hasRole("USER")
        .anyRequest().authenticated();
```

---

## 🏷 4. Securing Business Logic with Annotations (35 minutes)

### Enable Method Security:

```java
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
```

### Sample Service:

```java
@PreAuthorize("hasRole('ADMIN')")
public void deleteRegistration(Long id) { ... }

@PreAuthorize("hasRole('USER') and #userId == authentication.name")
public void updateProfile(String userId, ...) { ... }
```

### Exercise:

* Annotate existing service methods from `/registration/list` or `/chat` flow
* Add authorization checks inside `AuthenticationController` alternatives (refactor where applicable)

---

## 🧩 5. JSP View Security with `<sec:authorize>` (20 minutes)

### Taglib Use:

```jsp
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<sec:authorize access="hasRole('ADMIN')">
    <a href="/admin/panel">Admin Panel</a>
</sec:authorize>
```

### Exercise:

* Secure the “Delete” and “Edit” buttons in JSP views
* Add logout button:

```jsp
<form action="${pageContext.request.contextPath}/logout" method="post">
  <input type="submit" value="Logout"/>
</form>
```

---

## 🧠 6. Authorization Decision Flow (25 minutes)

### Concepts:

* Spring Security's decision flow: `AccessDecisionManager` → voters
* Roles: `RoleVoter`, `WebExpressionVoter`
* Custom voters for contextual decisions (e.g., time of day)

### Optional Exercise:

Implement `BusinessHoursVoter` to restrict `/chat` access outside 9–5.

---

## 🗂 7. (Advanced) ACLs and Object-Level Security (Optional – 30 minutes)

### Concepts:

* ACLs: User-specific permissions on entities
* Useful for per-resource permissions (e.g., only the owner can edit a registration)

### Exercise:

* Add a dummy ACL config using `MutableAclService`
* Annotate a method with:

```java
@PreAuthorize("hasPermission(#registration, 'WRITE')")
```

---

## ✅ 8. Wrap-Up and Q\&A (15 minutes)

### Recap:

| Feature            | Legacy (Lesson 1)      | Spring Security (Lesson 2) |
| ------------------ | ---------------------- | -------------------------- |
| Login Filter       | `AuthenticationFilter` | `formLogin()`              |
| Session Management | Manual                 | Spring-managed             |
| Logout             | Manual controller      | `.logout()`                |
| Authorization      | Manual `if` checks     | Annotations, expressions   |
| View Security      | N/A                    | `<sec:authorize>`          |

---

## 📦 Instructor Notes

* **Base project**: `charity-springjpa` (includes full `SecurityConfig`)
* **Dependencies**: already defined in provided `pom.xml`
* **JDBC Auth**: `USER_ADMIN` table, queried via `JdbcUserDetailsManager`
* **Form Login**: overrides `/login`, redirects to `/registration/list`

---

## 📘 Bonus Reading / Follow-Up

* Spring Security Reference: [https://docs.spring.io/spring-security/reference/](https://docs.spring.io/spring-security/reference/)
* Jakarta EE 8 Specs: [https://jakarta.ee/specifications/](https://jakarta.ee/specifications/)
* ACL Module: [https://docs.spring.io/spring-security/site/docs/current/reference/html5/#domain-acls](https://docs.spring.io/spring-security/site/docs/current/reference/html5/#domain-acls)

