# 6. Simplifying Authentication Using Filters

In this section, we will explore how to simplify user authentication in the [charity-registration](https://github.com/NicorDesigns/javawebdevcourse/tree/jee8web-filters-end/charity-registration) application by leveraging filters. Instead of embedding login validation logic within the `doGet()` or `doPost()` methods of our servlets, we'll centralize it in a filter, making our code more modular and maintainable.

## Code Overview

Let's take a closer look at the relevant components of the charity-registration application:

### Servlets

- [RegistrationServlet.java](https://github.com/NicorDesigns/javawebdevcourse/blob/jee8web-filters-end/charity-registration/src/main/java/com/nicordesigns/RegistrationServlet.java): In this servlet, the login validation code has been commented out. The servlet will no longer handle the authentication process directly.

- [SessionListServlet.java](https://github.com/NicorDesigns/javawebdevcourse/blob/jee8web-filters-end/charity-registration/src/main/java/com/nicordesigns/SessionListServlet.java): Similar to the RegistrationServlet, the login validation code is also commented out here.

### Authentication Filter

- [AuthenticationFilter.java](https://github.com/NicorDesigns/javawebdevcourse/blob/jee8web-filters-end/charity-registration/src/main/java/com/nicordesigns/AuthenticationFilter.java): This filter now contains the login validation logic. It intercepts requests to protected resources and ensures that users are authenticated before granting access.

### Configuration

- [Configurator.java](https://github.com/NicorDesigns/javawebdevcourse/blob/jee8web-filters-end/charity-registration/src/main/java/com/nicordesigns/Configurator.java): We use this class to register our AuthenticationFilter and configure URL mappings, specifying which URLs should be protected by the filter.

## Testing the Updated App

To test our updated charity-registration application, follow these steps:

1. **Deploy the Application:** Deploy the application to your servlet container, such as Apache Tomcat.

2. **Access the Login Page:** Visit [http://localhost:8080/charity-registration/login](http://localhost:8080/charity-registration/login). This URL is now the entry point for user authentication.

3. **Test Authentication:** Enter valid login credentials and submit the form to test the authentication process. The AuthenticationFilter will validate the user's credentials.

By centralizing the authentication logic in the AuthenticationFilter and configuring URL mappings in Configurator, we've simplified the authentication process in our application. This approach enhances code organization, maintainability, and security by ensuring that only authenticated users can access protected resources.

In conclusion, using filters for authentication is a powerful technique to streamline user access control in web applications. It abstracts the authentication logic from servlets, making code more modular and promoting security best practices.