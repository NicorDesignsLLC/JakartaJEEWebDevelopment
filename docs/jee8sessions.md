#### Maintaining State Using Sessions Part 1

#### 1. Why sessions are necessary 

[Slide 1](https://docs.google.com/presentation/d/132vh08RQrLJzmMjAGCOXUawpuQcdTPzqfOEiKb92eKY/edit?pli=1#slide=id.g11856de3b86_0_35)

- HTTP is a stateless protocol, and HTTP sessions provide a way to track users, such as an Amazon shopping cart.
- HTTP sessions are used to maintain state, such as the items in a shopping cart.
- HTTP sessions are a way to remember users, for example, their Amazon user name.
- HTTP sessions are used to manage workflow, as demonstrated in our example of registering a charity in an online database.

#### 2. Working with cookies and URL parameters 

[Slide 2](https://docs.google.com/presentation/d/132vh08RQrLJzmMjAGCOXUawpuQcdTPzqfOEiKb92eKY/edit?pli=1#slide=id.g118723741ed_0_5)

- A session is a data object maintained by the server or web application, containing user-related information.
- To enable the browser to "track" the session, the server generates a SessionID string that is sent back to the browser on every request.

```markdown
```java
class HttpSession {
  +invalidate(): void
  +getAttribute(name: String): Object
  +getAttributeNames(): Enumeration<String>
  +getCreationTime(): long
  +getId(): String
  +getLastAccessedTime(): long
  +getMaxInactiveInterval(): int
  +removeAttribute(name: String): void
  +setAttribute(name: String, value: Object): void
  +setMaxInactiveInterval(interval: int): void
}

note "Enumeration is a Java interface representing a sequence of elements." as Enumeration

HttpSession "1" --> "*" Enumeration : returns
```

- HTTP sessions are a way to remember users, such as their Reddit user name.
- HTTP sessions are used to manage workflow, as demonstrated in our example of registering a charity in an online database.

[Slide 3](https://docs.google.com/presentation/d/132vh08RQrLJzmMjAGCOXUawpuQcdTPzqfOEiKb92eKY/edit?pli=1#slide=id.g11dafb0213e_0_0) 

1. HTTP 1.1 defines session cookies [HTTP Cookie](https://en.wikipedia.org/wiki/HTTP_cookie), which can be sent from the web server to the browser and stored locally by the browser to be sent back. This is how the JSESSIONID will be "persisted" on the client browser side during a session.
2. SessionID is passed in the URL query string from the server to the client-side web browser after a session has been established on the web server. This process is called URL rewriting and can be used when cookies have been disabled on the client user browser. The JEE8 platform includes the API tools to handle sessions.
3. Security is a major concern when using sessions. [OWASP Security Testing Guide](https://owasp.org/www-project-web-security-testing-guide/latest/4-Web_Application_Security_Testing/06-Session_Management_Testing/01-Testing_for_Session_Management_Schema) provides useful information on session management testing, including session hijacking attacks.
4. The best way to secure your session in Tomcat 9 is with SSL. [SSL/TLS Configuration How-To](https://tomcat.apache.org/tomcat-9.0-doc/ssl-howto.html) offers guidance on configuring SSL.

#### 3. How to store data in a session 

This is where we start adding session data in our charity-registration web application example.

##### 1. Ensure we have the `<jsp-config>` tag, the `base.jspf` file, and the redirect in our `index.jsp` landing page.

##### 2. We configure our session in the `web.xml` as in the following example:  [Oracle Weblogic documentation](https://docs.oracle.com/cd/E24329_01/web.1211/e21049/web_xml.htm#WBAPP510)
		
```xml
<session-config>
    <!-- Time before an inactive session is invalidated -->
    <session-timeout>30</session-timeout>
    <!-- When using tracking-mode of COOKIE -->
    <cookie-config>
        <!-- Custom name of the Session --> 
        <name>JSESSIONID</name>
        <domain>nicordesigns.com</domain>
        <path>/registrations</path>
        <!-- Adds a comment to a cookie -->
        <comment>This is a comment</comment>
        <http-only>true</http-only>
        <secure>false</secure>
        <!-- Time a cookie will be persisted on the client browser -->
        <max-age>1800</max-age>
    </cookie-config>
    <!-- Specifies how the server will implement the Session URL, COOKIE, SSL, and order -->
    <tracking-mode>COOKIE</tracking-mode>
</session-config>
```

Here, the tags are optional, but the order is required. Additionally, some configurations can be done programmatically using the [ServletContext](https://javaee.github.io/javaee-spec/javadocs/javax/servlet/ServletContext.html).

An example of setting `session-timeout` programmatically:

```java
HttpSession session = request.getSession();
session.setMaxInactiveInterval(10 * 60);
```

We will be using the `charity-session` module to demonstrate this.

```xml
<session-config>
    <session-timeout>30</session-timeout>
    <cookie-config>
        <http-only>true</http-only>
    </cookie-config>
    <tracking-mode>COOKIE</tracking-mode>
</session-config>
```

##### 3. Storing and Retrieving Data

CharitySessionServlet

```java
@WebServlet(
    name = "charitySessionServlet",
    urlPatterns = "/charitySession"
)
public class CharitySessionServlet extends HttpServlet {
    private final Map<Integer, String> categories = new Hashtable<>();

    public CharitySessionServlet() {
        this.categories.put(1, "Animals");
        this.categories.put(2, "Arts, Culture, Humanities");
        this.categories.put(3, "Community Development");
        this.categories.put(4, "Education");
        this.categories.put(5, "Environment");
        this.categories.put(6, "Health");
    }
    
    // Rest of the class implementation...
}
```

##### 4. Using Sessions in the Charity Session Servlet

```java
@Override
protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    String action = request.getParameter("action");
    if (action == null)
        action = "browse

";

    switch (action) {
        case "addToCharitySession":
            this.addToCharitySession(request, response);
            break;

        case "emptyCharitySessionObject":
            this.emptyCharitySessionObject(request, response);
            break;

        case "viewCharitySession":
            this.viewCharitySession(request, response);
            break;

        case "browse":
        default:
            this.browse(request, response);
            break;
    }
}
```

##### 5. Using Sessions in JSP

browse.jsp

```jsp
<%@ page import="java.util.Map" %>
<!DOCTYPE html>
<html>
<head>
    <title>Charity Category List</title>
</head>
<body>
    <h2>Charity Category List</h2>
    <a href="<c:url value="/charitySession?action=viewCharitySession" />">
    View Charity Session Data Object</a><br /><br />
    <%
        @SuppressWarnings("unchecked")
        Map<Integer, String> categories =
                (Map<Integer, String>) request.getAttribute("categories");

        for (int id : categories.keySet()) {
            %><a href="<c:url value="/charitySession">
                <c:param name="action" value="addToCharitySession" />
                <c:param name="categoryId" value="<%= Integer.toString(id) %>"/>
            </c:url>"><%= categories.get(id) %></a><br /><%
        }
    %>
</body>
</html>
```

viewCharitySessionObject.jsp

```jsp
<%@ page import="java.util.Map" %>
<!DOCTYPE html>
<html>
<head>
    <title>View Charity Session Object</title>
</head>
<body>
    <h2>View Charity Session Object</h2>
    <a href="<c:url value="/charitySession" />">Charity Category List</a><br /><br />
    <a href="<c:url value="/charitySession?action=emptyCharitySessionObject" />">
    Empty Charity Session Object</a><br /><br />
    <%
        @SuppressWarnings("unchecked")
        Map<Integer, String> categories =
                (Map<Integer, String>) request.getAttribute("categories");
        @SuppressWarnings("unchecked")
        Map<Integer, Integer> categoryHolder =
                (Map<Integer, Integer>) session.getAttribute("categoryHolder");

        if (categoryHolder == null || categoryHolder.size() == 0)
            out.println("Your category holder is empty.");
        else {
            for (int id : categoryHolder.keySet()) {
                out.println(categories.get(id) + " (qty: " + categoryHolder.get(id) +
                        ")<br />");
            }
        }
    %>
</body>
</html>
```

- `HttpSession.getId()` retrieves the Session Id.
- `getCreationTime()`: Gets the time when the session was created.
- `getLastAccessedTime()`: Gets the last time the user accessed the session.
- `isNew()`: Returns `true` if the session was created during the current request.
- `getMaxInactiveInterval()`: Gets the maximum time that the session can be inactive.
- `setMaxInactiveInterval()`: Sets the time that the session can be inactive (`<session-timeout>`).
- `invalidate()`: Removes the current session and its data.

##### 5. Compiling, testing, and debugging our charity-session web application

We will examine all the functionality provided by our app and also explore what happens with the session when you close the browser.

##### [Maintaining State Using Sessions Part 1 - Git Branch](https://github.com/NicorDesignsLLC/JakartaJEEWebDevelopment/tree/jee8web-session-part1)    

