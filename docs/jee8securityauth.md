# Lesson 1: Authenticating Users in a Legacy Spring Web Application

---

## 🎯 Lesson Objectives

By the end of this lesson, you will be able to:

✅ Understand your current custom authentication flow

✅ Identify how user login, logout, and session management are handled

✅ Recognize the role of `AuthenticationFilter` and `UserAdminPrincipal`

✅ Appreciate the importance of login form validation

✅ Prepare for modernizing authentication with Spring Security

---

## 1️⃣ Current Authentication Architecture

Your current application uses **custom authentication logic** to protect resources and manage user sessions. Let’s break it down.

---

### 📦 1.1 Bootstrap Configuration

`com.nicordesigns.site.config.Bootstrap` configures your **servlet contexts and filters**, including:

* **Authentication Filter**:
  Ensures protected URLs (like `/registration`, `/chat`, `/session`) are accessible only by authenticated users.

* **Logging Filter**:
  Captures request/response logs.

✅ **Code Snippet:**

```java
FilterRegistration.Dynamic authenticationFilter = container.addFilter("authenticationFilter", new AuthenticationFilter());
authenticationFilter.addMappingForUrlPatterns(null, false,
    "/registration", "/registration/*", "/chat", "/chat/*", "/session", "/session/*");
```

---

### 🛡️ 1.2 Authentication Filter

`com.nicordesigns.site.filters.AuthenticationFilter` is a **servlet filter** that:

✅ Checks if the session contains a valid `Principal`
✅ Redirects unauthenticated users to the login page (`/login`)
✅ Wraps the request to expose the authenticated principal

✅ **Code Snippet:**

```java
@Override
public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {
    HttpSession session = ((HttpServletRequest) request).getSession(false);
    final Principal principal = UserAdminPrincipal.getPrincipal(session);
    if (principal == null) {
        ((HttpServletResponse) response).sendRedirect(((HttpServletRequest) request).getContextPath() + "/login");
    } else {
        chain.doFilter(new HttpServletRequestWrapper((HttpServletRequest) request) {
            @Override
            public Principal getUserPrincipal() {
                return principal;
            }
        }, response);
    }
}
```

---

### 👤 1.3 UserAdminPrincipal

`com.nicordesigns.site.UserAdminPrincipal` manages the **user identity**:

✅ Implements `Principal`
✅ Stores username
✅ Provides static helpers to store/retrieve from `HttpSession`

✅ **Code Snippet:**

```java
public static Principal getPrincipal(HttpSession session) {
    return session == null ? null : (Principal) session.getAttribute("com.nicordesigns.user.principal");
}

public static void setPrincipal(HttpSession session, Principal principal) {
    session.setAttribute("com.nicordesigns.user.principal", principal);
}
```

---

### 🔑 1.4 Login Form (JSP)

Your `login.jsp` uses **Spring tags** for:

✅ Username and password input
✅ Field-level and general error messages
✅ Validation error display

✅ **Example Snippet:**

```jsp
<form:form method="post" modelAttribute="loginForm">
    <form:label path="username">Username</form:label><br/>
    <form:input path="username" /><br/>
    <form:errors path="username" cssClass="errors" />

    <form:label path="password">Password</form:label><br/>
    <form:password path="password" /><br/>
    <form:errors path="password" cssClass="errors" /><br/>

    <input type="submit" value="Login" />
</form:form>
```

---

### ⚙️ 1.5 AuthenticationController

The `AuthenticationController` (`com.nicordesigns.site.AuthenticationController`) manages:

✅ **Login**
✅ **Logout**
✅ **Login form validation**
✅ **Session creation & management**

✅ **Key Features:**

* **GET `/login`**:

  * Shows login form
  * Redirects if already logged in

* **POST `/login`**:

  * Validates form fields
  * Uses `AuthenticationService` to authenticate
  * Stores principal in session and regenerates session ID
  * Redirects to `/registration/list` on success
  * Displays errors on failure

* **GET `/logout`**:

  * Invalidates session
  * Redirects to `/login`

✅ **Code Snippet:**

```java
@PostMapping(value = "login")
public ModelAndView login(@ModelAttribute("loginForm") @Valid LoginForm form,
                          Errors errors,
                          Map<String, Object> model,
                          HttpSession session,
                          HttpServletRequest request) {
    log.info("login POST");

    if (UserAdminPrincipal.getPrincipal(session) != null)
        return getRegistrationRedirect();

    if (errors.hasErrors()) {
        form.setPassword(null);
        return new ModelAndView("login");
    }

    Principal principal;
    try {
        principal = authenticationService.authenticate(form.getUsername(), form.getPassword());
    } catch (ConstraintViolationException e) {
        form.setPassword(null);
        model.put("validationErrors", e.getConstraintViolations());
        return new ModelAndView("login");
    }

    if (principal == null) {
        form.setPassword(null);
        model.put("loginFailed", true);
        return new ModelAndView("login");
    }

    UserAdminPrincipal.setPrincipal(session, principal);
    request.changeSessionId();
    return getRegistrationRedirect();
}
```

---

### 🛑 Current Flow Summary

✅ **Login**:

* Authenticates via `AuthenticationService`.
* Stores user in session (`UserAdminPrincipal`).
* Session ID changed to protect against fixation attacks.

✅ **Session Validation**:

* `AuthenticationFilter` ensures only logged-in users can access protected resources.

✅ **Logout**:

* Invalidates session and redirects to login page.

---

## 🚀 Next Steps

Now that we have fully documented your **existing login and authentication approach**, we’re ready to:

✅ Identify **gaps** in security (e.g., CSRF protection, concurrent sessions)

✅ Integrate **Spring Security** gradually, starting with basic `SecurityFilterChain` to standardize authentication

✅ Migrate existing **`AuthenticationController` logic** to Spring Security’s form login flow

---

