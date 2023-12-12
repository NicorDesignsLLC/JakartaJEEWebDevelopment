## 5. Creating a WebSocket Chat App

In this section, we will leverage the Tomcat 9 Chat Example App as a reference to integrate a chat feature into our Charity Registration application.

### Maven POM Dependencies

Ensure that your Maven POM includes the necessary dependencies for a Jakarta JEE8 WebSocket Application. This involves a server-side Servlet implementation and a client-side JSP with JavaScript. You can find the POM file [here](https://github.com/NicorDesignsLLC/JakartaJEEWebDevelopment/blob/docs/charity-registration/pom.xml).

### Updating Templates

Update your templates with the required JavaScript dependencies for an interactive WebSocket Chat Room. The changes also include the addition of new CSS stylesheets. Check the modifications in the [template directory](https://github.com/NicorDesignsLLC/JakartaJEEWebDevelopment/tree/docs/charity-registration/src/main/webapp/WEB-INF/tags/template).

### Servlet Implementation

Review the Servlet implementation [here](https://github.com/NicorDesignsLLC/JakartaJEEWebDevelopment/blob/docs/charity-registration/src/main/java/com/nicordesigns/servlets/ChatServlet.java). Pay attention to the logic related to the `list.jsp` and `chat.jsp` available [here](https://github.com/NicorDesignsLLC/JakartaJEEWebDevelopment/blob/docs/charity-registration/src/main/webapp/WEB-INF/jsp/view/chat.jsp) and [here](https://github.com/NicorDesignsLLC/JakartaJEEWebDevelopment/blob/docs/charity-registration/src/main/webapp/WEB-INF/jsp/view/list.jsp).

### POJO Classes

Explore the relevant POJO classes [here](https://github.com/NicorDesignsLLC/JakartaJEEWebDevelopment/tree/docs/charity-registration/src/main/java/com/nicordesigns/chat). Focus on understanding the `ChatMessage` and `ChatEndpoint` and how they facilitate communication between the client-side JavaScript and the server-side Java implementation.

### Running the App

Follow along in the course video to launch and run the app where we will set up a session between two browsers to test the functionality.

