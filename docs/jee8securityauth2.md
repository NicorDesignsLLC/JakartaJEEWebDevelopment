# Lesson 2: Step-by-Step Integration of Spring Security for Authentication

---

## 🎯 Lesson Objectives

By the end of this section, you will be able to:

✅ Identify **security gaps** in our existing authentication approach

✅ Gradually integrate **Spring Security** with `SecurityFilterChain` and JDBC-based authentication

✅ **Migrate** the custom login flow (`AuthenticationController`) to Spring Security’s form login

✅ Use our existing `USER_ADMIN` table as the data source

✅ Understand the code updates and test them incrementally

---

## 1️⃣ Current Security Gaps

Before diving in, here are some potential gaps in our custom approach:

❌ **CSRF Protection**: No CSRF tokens in forms—Spring Security can automatically handle this.

❌ **Session Fixation**: Although you’re already using `request.changeSessionId()`, Spring Security offers a standard, configurable approach.

❌ **Concurrent Sessions**: Not limited—Spring Security can manage maximum sessions per user.

❌ **Password Storage**: Currently stored in plaintext—Spring Security uses password hashing (e.g., BCrypt) out-of-the-box.

❌ **Role-based Authorization**: Not covered yet—Spring Security supports roles/authorities.

---

## 2️⃣ Step 1: Add Spring Security Dependencies

✅ **Maven `pom.xml` dependencies**:

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
<dependency>
    <groupId>org.springframework.security</groupId>
    <artifactId>spring-security-crypto</artifactId>
    <version>5.8.0</version>
</dependency>
```

---

## 3️⃣ Step 2: Basic Spring Security Setup

✅ **Create a Spring Security configuration class**:

```java
package com.nicordesigns.site.config;

import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/login", "/resources/**").permitAll()
                .anyRequest().authenticated()
            .and()
            .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/registration/list")
                .failureUrl("/login?error")
                .permitAll()
            .and()
            .logout()
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            .and()
            .csrf()  // ✅ CSRF protection enabled by default
            .and()
            .sessionManagement()
                .maximumSessions(1); // ✅ Limit concurrent sessions

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(DataSource dataSource) {
        JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
        users.setUsersByUsernameQuery(
            "SELECT USERNAME, PASSWORD, true as enabled FROM USER_ADMIN WHERE USERNAME = ?");
        users.setAuthoritiesByUsernameQuery(
            "SELECT USERNAME, 'ROLE_USER' FROM USER_ADMIN WHERE USERNAME = ?");
        return users;
    }
}
```

---

### ✅ Immediate **Demonstration**

**Test**:

* Visit `/login`—Spring Security will **reuse our existing `login.jsp`** (since it’s mapped to `/login`).
* Use credentials from our `USER_ADMIN` table:

  * `Nicolaas / Black`
  * `Danette / White`
  * `Tom / Green`

✅ You’ll be **redirected** to `/registration/list` on success!

---

## 4️⃣ Step 3: Update our `USER_ADMIN` Table with BCrypt Passwords

Currently, passwords in `USER_ADMIN` are stored in plaintext. Let’s **update them to BCrypt**:

✅ Use an online tool (e.g., [bcrypt-generator.com](https://bcrypt-generator.com/)) or Java code to hash them:

```java
public static void main(String[] args) {
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    System.out.println(encoder.encode("Black")); // example output: $2a$10$...
}
```

✅ **SQL Update**:

```sql
UPDATE charitydb.USER_ADMIN SET PASSWORD = '$2a$10$...' WHERE USERNAME = 'Nicolaas';
UPDATE charitydb.USER_ADMIN SET PASSWORD = '$2a$10$...' WHERE USERNAME = 'Danette';
UPDATE charitydb.USER_ADMIN SET PASSWORD = '$2a$10$...' WHERE USERNAME = 'Tom';
```

✅ **Demonstration**:
Try logging in again using the **hashed** passwords.

---

## 5️⃣ Step 4: Retire the Custom `AuthenticationController` Logic

Now that Spring Security’s **form login** and JDBC authentication are working:

✅ **Comment out** or **remove** the `AuthenticationController` `POST /login` logic—it’s no longer needed!
✅ Spring Security automatically handles:

* Validating credentials against `USER_ADMIN` (via JDBC)
* Redirecting to the success URL or back to `/login?error`
* Creating the `Principal` in the `HttpSession`

✅ **Your `login.jsp` form** remains unchanged—it’s **automatically** handled by Spring Security.

---

## 6️⃣ Recap and Next Steps

✅ **Gaps filled**:

* CSRF protection (auto by Spring Security)
* Session fixation protection (`sessionManagement()` & `changeSessionId()`)
* Concurrent sessions limited to 1
* Passwords **securely stored** with BCrypt

✅ **Future tasks**:

* Migrate other filters (like `AuthenticationFilter`) to Spring Security’s filter chain
* Add role-based access control (e.g., `/admin/**` requires `ROLE_ADMIN`)
* Externalize LDAP/OIDC integration for corporate logins

---

### 🚀 Final Demonstration

🔎 Test the new Spring Security flow:

1️⃣ Visit `/login`

2️⃣ Log in with an updated **hashed** password from `USER_ADMIN`

3️⃣ Access `/registration/list`—✅ protected!

4️⃣ Test `/logout`—✅ redirects back to `/login`

5️⃣ Confirm `session fixation` protection (`changeSessionId()` logs confirm new session)


