# 6. Simplifying Authentication Using Filters

We will now update the [charity-registration](https://github.com/NicorDesigns/javawebdevcourse/tree/jee8web-filters-end/charity-registration) to use Filters for authentication the User login instead of checking the login values inside the doGet() or doPost() methods of our Servlets.

When we look at our [charity-registration](https://github.com/NicorDesigns/javawebdevcourse/tree/jee8web-filters-end/charity-registration) code, we see the login code is commented out in:

[RegistrationServlet.java](https://github.com/NicorDesigns/javawebdevcourse/blob/jee8web-filters-end/charity-registration/src/main/java/com/nicordesigns/RegistrationServlet.java)

and 

[SessionListServlet.java](https://github.com/NicorDesigns/javawebdevcourse/blob/jee8web-filters-end/charity-registration/src/main/java/com/nicordesigns/SessionListServlet.java)

We have moved the login validation code into our

[AuthenticationFilter.java](https://github.com/NicorDesigns/javawebdevcourse/blob/jee8web-filters-end/charity-registration/src/main/java/com/nicordesigns/AuthenticationFilter.java)

and we have used

[Configurator.java](https://github.com/NicorDesigns/javawebdevcourse/blob/jee8web-filters-end/charity-registration/src/main/java/com/nicordesigns/Configurator.java)

To register our Filter and configure the URL mappings.

We can test our updated app:

http://localhost:8080/charity-registration/login

