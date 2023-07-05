### Maintaining State Using Sessions in our JEE 8 Web App Module - Part 1

Use these (but for JEE8) to update these code examples:

[Jakarta Servlet Specification - Sessions](https://jakarta.ee/specifications/servlet/5.0/jakarta-servlet-spec-5.0.html#sessions)

We will now return to our charity registration example and start to introduce State and Session Management

##### [Maintaining State Using Sessions Start Branch](https://github.com/NicorDesigns/javawebdevcourse/tree/jee8web-session-management-start)

#### 1. Why sessions are necessary (Slide 2)

###### HTTP is a stateless protocol, HTTPSessions is a way to track users for example Amazon Shopping Cart
###### HTTPSessions are a way to maintain state (items in shopping cart)
###### HTTPSessions are a way to remember Users for example your Reddit User Name
###### HTTPSessions are a way to manage Workflow as in our example of Registering a Charity in an Online DB


#### 2. Working with cookies and URL parameters (Slide 3) 

###### A session is a data object maintained by the server or web application (user name etc)
###### For the users browser to be able to "track" the server generates a SessionID string that gets send back to the browser on every request 

#(Add PlantUML diagram here)

###### HTTPSessions are a way to remember Users for example your Reddit User Name
###### HTTPSessions are a way to manage Workflow as in our example of Registering a Charity in an Online DB

1. HTTP1.1 defines session cookies [HTTP Cookie](https://en.wikipedia.org/wiki/HTTP_cookie) which can be sent from
the web server to the browser and stored locally by the browser to be sent back. This is how the JSESSIONID will
get to be "persisted" on the client browser side during a session  
2. SessionID is passed in the URL query string from the server to the client side web browser after a session has
been established on the web server, this process is called URL re-writing and can be used when cookies has been
disabled on the client user browser. The JEE8 platform includes the API tools to handle Sessions.
3. Security is a major concern when using Sessions [OWASP Security Testing Guide](https://owasp.org/www-project-web-security-testing-guide/latest/4-Web_Application_Security_Testing/06-Session_Management_Testing/01-Testing_for_Session_Management_Schema) and as such probably needs a whole separate course [Session Hijacking](https://owasp.org/www-community/attacks/Session_hijacking_attack)  
[All forms of Attacks](https://owasp.org/www-community/attacks/)
4. The best way to secure your session in Tomcat 9 is with SSL [SSL/TLS Configuration How-To](https://tomcat.apache.org/tomcat-9.0-doc/ssl-howto.html)



#### 3. How to store data in a session 

This is where we start adding Session data in our  charity-registration web application example
 
##### 1. Ensure we have the <jsp-config> tag, the base.jspf file and the re-direct in our index.jsp landing page 

##### 2. We configure our session in the web.xml as in the following example:  [Oracle Weblogic documentation](https://docs.oracle.com/cd/E24329_01/web.1211/e21049/web_xml.htm#WBAPP510)
		
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
            <!-- Time a cookie will be persisted on the client browser-->
            <max-age>1800</max-age>
        </cookie-config>
        <!-- Specifies how server will implement the Session URL, COOKIE, SSL and order -->
        <tracking-mode>COOKIE</tracking-mode>
    	</session-config>

here the tags are optional but the order is required, and also not all this can also be done programmatically with
the [ServletContext](https://javaee.github.io/javaee-spec/javadocs/javax/servlet/ServletContext.html) as well.

An example of setting session-timeout programmatically:

		HttpSession session = request.getSession();
		session.setMaxInactiveInterval(10*60);

We will be using the charity-session module to demonstrate here

	<session-config>
        <session-timeout>30</session-timeout>
        <cookie-config>
            <http-only>true</http-only>
        </cookie-config>
        <tracking-mode>COOKIE</tracking-mode>
    </session-config>


##### 3. Storing and Retrieving Data

CharitySessionServlet

		@WebServlet(
        name = "charitySessionServlet",
        urlPatterns = "/charitySession"
		)
		public class CharitySessionServlet extends HttpServlet
		{
	 		private final Map<Integer, String> categories = new Hashtable<>();
	
	    public CharitySessionServlet()
	    {
	        this.categories.put(1, "Animals");
	        this.categories.put(2, "Arts, Culture, Humanities");
	        this.categories.put(3, "Community Development");
	        this.categories.put(4, "Education");
	        this.categories.put(5, "Environment");
	        this.categories.put(6, "Health");
	    }
		
##### 4. Using Sessions in the Charity Session Servlet


	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        String action = request.getParameter("action");
        if(action == null)
            action = "browse";

        switch(action)
        {
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
		

##### 4. Using Sessions in JSP

browse.jsp

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
		                    (Map<Integer, String>)request.getAttribute("categories");
		
		            for(int id : categories.keySet())
		            {
		                %><a href="<c:url value="/charitySession">
		                    <c:param name="action" value="addToCharitySession" />
		                    <c:param name="categoryId" value="<%= Integer.toString(id) %>"/>
		                </c:url>"><%= categories.get(id) %></a><br /><%
		            }
		        %>
		    </body>
		</html>
		
viewCharitySessionObject.jsp

		
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
	                    (Map<Integer, String>)request.getAttribute("categories");
	            @SuppressWarnings("unchecked")
	            Map<Integer, Integer> categoryHolder =
	                    (Map<Integer, Integer>)session.getAttribute("categoryHolder");
	
	            if(categoryHolder == null || categoryHolder.size() == 0)
	                out.println("Your category holder is empty.");
	            else
	            {
	                for(int id : categoryHolder.keySet())
	                {
	                    out.println(categories.get(id) + " (qty: " + categoryHolder.get(id) +
	                            ")<br />");
	                }
	            }
	        %>
	    </body>
	</html>
	
HttpSession.getId() retrieves the Session Id
getCreationTime
getLastAccessedTime- the last time the User Accessed the session
isNew - true if the session was created during the current request
getMaxInterval - max time that the session can be inactive
setMaxInterval - set the time the session can be inactive <session-timeout>
invalidate - removes the current session and its data


	
		
##### 5. Compiling, testing and debugging our charity-session web application

We look at all the functionality provided by our app and we also look at what happens with the session 
when you close the browser
     	
Check in the end Git branch of this slide show 

##### [Maintaining State Using Sessions Finish Branch](https://github.com/NicorDesigns/javawebdevcourse/tree/jee8web-session-management-finish)

    

