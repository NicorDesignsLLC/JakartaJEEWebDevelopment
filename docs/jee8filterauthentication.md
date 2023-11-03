# 6. Simplifying Authentication Using Filters

In this section, we will explore how to simplify user authentication in the [charity-registration](https://github.com/NicorDesignsLLC/JakartaJEEWebDevelopment/tree/main/charity-registration) application by leveraging filters. 

Instead of embedding login validation logic within the `doGet()` or `doPost()` methods of our servlets, we'll centralize it in a filter, making our code more modular and maintainable.

## Code Overview

Let's take a closer look at the relevant components of the charity-registration application:

### Servlets

- [CharityRegistrationServlet.java](https://github.com/NicorDesignsLLC/JakartaJEEWebDevelopment/blob/jee8web-filters/charity-registration/src/main/java/com/nicordesigns/CharityRegistrationServlet.java): 
In this servlet, we do not have login validation code. 
The servlet will not handle the authentication process directly.

- [SessionListServlet.java](https://github.com/NicorDesignsLLC/JakartaJEEWebDevelopment/blob/jee8web-filters/charity-registration/src/main/java/com/nicordesigns/SessionListServlet.java): 
The previous login validation demonstration code is commented out here.

### Authentication Filter

- [AuthenticationFilter.java](https://github.com/NicorDesignsLLC/JakartaJEEWebDevelopment/blob/jee8web-filters/charity-registration/src/main/java/com/nicordesigns/filters/AuthenticationFilter.java): 
This filter now contains the login validation logic. 
It intercepts requests to protected resources and ensures that users are authenticated before granting access.

### Configuration

- [Configurator.java](https://github.com/NicorDesignsLLC/JakartaJEEWebDevelopment/blob/jee8web-filters/charity-registration/src/main/java/com/nicordesigns/filters/Configurator.java): We use this class to register our AuthenticationFilter and configure URL mappings, specifying which URLs should be protected by the filter.

## Testing the Updated App

To test our updated charity-registration application, follow these steps:

1. **Deploy the Application:** Deploy the application to your servlet container, such as Apache Tomcat.

2. **Access the Login Page:** Visit [http://localhost:8080/charity-registration/login](http://localhost:8080/charity-registration/login). This URL is now the entry point for user authentication.

3. **Test Authentication:** Enter valid login credentials and submit the form to test the authentication process. The AuthenticationFilter will validate the user's credentials.

By centralizing the authentication logic in the AuthenticationFilter and configuring URL mappings in Configurator, we've simplified the authentication process in our application. This approach enhances code organization, maintainability, and security by ensuring that only authenticated users can access protected resources.

In conclusion, using filters for authentication is a powerful technique to streamline user access control in web applications. It abstracts the authentication logic from servlets, making code more modular and promoting security best practices.