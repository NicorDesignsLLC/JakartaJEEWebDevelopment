# Lesson 1: Authenticating Users with Spring Security

## 🎯 Lesson Objectives

By the end of this lesson, you will be able to:

✅ Configure Spring Security authentication for a legacy Java application

✅ Implement form-based login, JDBC, LDAP, and OpenID authentication mechanisms

✅ Protect against session fixation attacks and limit concurrent user sessions

✅ Enable "Remember Me" functionality for persistent user sessions

✅ Create a custom authentication provider for specialized use cases

✅ Mitigate cross-site request forgery (CSRF) attacks using Spring Security

---

## 1️ Adding Spring Security Authentication to Your Application

### 📝 Overview

Spring Security provides a robust framework for authenticating users by validating credentials against various identity stores (e.g., in-memory, database, LDAP). This section walks you through setting up authentication in a legacy Java application using Spring 5 and Jakarta EE 8.

### ⚙️ Step-by-Step Setup

#### 1️⃣    Add Dependencies

For Maven-based legacy projects, include:

```xml
<dependency>
    <groupId>org.springframework.security</groupId>
    <artifactId>spring-security-web</artifactId>
    <version>5.8.0</version>
</dependency>
<dependency>
    <groupId>org.springframework.security</groupId>
    <artifactId>spring-security-config</artifactId>
    <version>5.8.0</version>
</dependency>
```

#### 2️⃣   Configure `SecurityFilterChain`

Use the modern bean-based configuration to define security rules:

```java
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/public/**").permitAll()
                .anyRequest().authenticated()
            .and()
            .formLogin()
                .loginPage("/login")
                .permitAll()
            .and()
            .logout()
                .permitAll();
        return http.build();
    }
}
```

#### 3️⃣    Test with In-Memory Authentication

```java
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Bean
public UserDetailsService userDetailsService() {
    User.UserBuilder users = User.withDefaultPasswordEncoder();
    InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
    manager.createUser(users.username("user").password("password").roles("USER").build());
    return manager;
}
```

#### 4️⃣    Create a Login Page

Create a simple JSP page (`login.jsp`):

```jsp
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<body>
    <h2>Login</h2>
    <form method="post" action="/login">
        <label>Username: <input type="text" name="username"/></label><br/>
        <label>Password: <input type="password" name="password"/></label><br/>
        <input type="submit" value="Login"/>
    </form>
    <c:if test="${param.error != null}">
        <p style="color:red;">Invalid credentials</p>
    </c:if>
</body>
</html>
```

---

## 2️⃣    Using Form Login, JDBC, LDAP, and OpenID

### 🔑 Form Login

* Configured above using `.formLogin()` in `SecurityFilterChain`.
* Customize further with `.defaultSuccessUrl("/home")` or `.failureUrl("/login?error")`.

### 🔗    JDBC Authentication

Configure a `DataSource` and `JdbcUserDetailsManager`:

```java
import javax.sql.DataSource;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

@Bean
public UserDetailsService userDetailsService(DataSource dataSource) {
    JdbcUserDetailsManager manager = new JdbcUserDetailsManager(dataSource);
    manager.setUsersByUsernameQuery("SELECT username, password, enabled FROM users WHERE username = ?");
    manager.setAuthoritiesByUsernameQuery("SELECT username, authority FROM authorities WHERE username = ?");
    return manager;
}
```

✅ Ensure your database has tables:

* `users` (username, password, enabled)
* `authorities` (username, authority)

### 🗂️   LDAP Authentication

Integrate with an LDAP server:

```java
import org.springframework.security.ldap.userdetails.LdapUserDetailsService;

@Bean
public UserDetailsService userDetailsService() {
    LdapUserDetailsService service = new LdapUserDetailsService();
    // Configure LDAP server URL, base DN, etc.
    return service;
}
```

✅ Requires `spring-security-ldap` dependency and LDAP server details.

### 🌐 OpenID Connect (OIDC)

Use OAuth 2.0/OpenID Connect for third-party identity providers:

```java
http
    .oauth2Login()
        .loginPage("/login")
        .defaultSuccessUrl("/home");
```

✅ Add `spring-security-oauth2-client` dependency and configure client details.

---

## 3️⃣ Protecting Against Session Fixation and Limiting User Sessions

### 🔒 Session Fixation Protection

Spring Security enables this by default:

```java
http
    .sessionManagement()
        .sessionFixation().changeSessionId();
```

### 🧑‍🤝‍🧑 Limiting Concurrent Sessions

Restrict to a single active session:

```java
http
    .sessionManagement()
        .maximumSessions(1)
        .expiredUrl("/login?expired");
```

✅ Requires a `SessionRegistry` bean.

---

## 4️⃣ Remembering Users Between Sessions

Enable "Remember Me" functionality:

```java
http
    .rememberMe()
        .key("uniqueAndSecretKey")
        .tokenValiditySeconds(86400); // 1 day
```

✅ Add a checkbox to the login form:

```html
<input type="checkbox" name="remember-me"/> Remember Me
```

---

## 5️⃣   Creating a Custom Authentication Provider

Implement a custom `AuthenticationProvider`:

```java
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

@Bean
public AuthenticationProvider customAuthProvider() {
    return new AuthenticationProvider() {
        @Override
        public Authentication authenticate(Authentication authentication) {
            String username = authentication.getName();
            String password = authentication.getCredentials().toString();
            // Custom logic (e.g., validate against external service)
            if ("user".equals(username) && "password".equals(password)) {
                return new UsernamePasswordAuthenticationToken(username, password, Collections.emptyList());
            }
            throw new BadCredentialsException("Invalid credentials");
        }

        @Override
        public boolean supports(Class<?> authentication) {
            return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
        }
    };
}
```

✅ Register it in `SecurityFilterChain`:

```java
http.authenticationProvider(customAuthProvider());
```

---

## 6️⃣ Mitigating Cross-Site Request Forgery (CSRF) Attacks

* CSRF protection is **enabled by default** in Spring Security.
* Ensure your JSP forms include the CSRF token:

```html
<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
```

✅ For REST APIs (stateless auth like JWT), disable CSRF:

```java
http.csrf().disable();
```

> **⚠️ Note:** Only disable CSRF for stateless APIs to avoid vulnerabilities.

---

## 🛠️ Hands-On Exercise

✅ Set up a Spring MVC project with Spring Security using the dependencies above.
✅ Configure form-based login with an in-memory user.
✅ Extend the configuration to use JDBC authentication with a sample `users` table.
✅ Enable "Remember Me" and test session persistence across browser restarts.
✅ Implement a custom `AuthenticationProvider` to validate credentials against a mock external API.

---

## 💡 Key Takeaways

* Spring Security supports multiple authentication mechanisms (form, JDBC, LDAP, OIDC).
* Session fixation and concurrent session limits enhance security for stateful applications.
* "Remember Me" improves user experience while maintaining security.
* Custom authentication providers enable integration with external systems.
* CSRF protection is critical for stateful applications; disable it only for stateless APIs.

---

## 👉 Next Steps

In the next lesson, we’ll explore **Authorization with Spring Security**, covering role-based access control, expression-based rules, and securing REST endpoints with OAuth 2.0 and JWT.

