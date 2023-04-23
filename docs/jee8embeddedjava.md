### Embedding Java within JSP in the JEE 8 Web App Module

We will explore how to use Java within JSP and find out why it is actually a bad idea

##### [Embedding Java within JSP Start Branch](https://github.com/NicorDesignsLLC/JakartaJEEWebDevelopment/tree/jee8-embedded-java-start)

#### 1. Using the Implicit variables in JSP 

[Implicit Objects](https://jakarta.ee/specifications/pages/3.0/jakarta-server-pages-spec-3.0.html#implicit-objects)

These variables are part of the JSP specification and their scope are limited to the JSP Service method,
which means they are not accessible within JSP declarations which have class scope

We will now take a look at the generated JSP Service method in our jee8webarchetype module


	public void _jspService(final javax.servlet.http.HttpServletRequest request, 
			final javax.servlet.http.HttpServletResponse response)
			
		// request and response - also accessible within JSP with limitations due the JSP parsing and execution logic 
			
      throws java.io.IOException, javax.servlet.ServletException {

	    if (!javax.servlet.DispatcherType.ERROR.equals(request.getDispatcherType())) {
	      final java.lang.String _jspx_method = request.getMethod();
	      if ("OPTIONS".equals(_jspx_method)) {
	        response.setHeader("Allow","GET, HEAD, POST, OPTIONS");
	        return;
	      }
	      if (!"GET".equals(_jspx_method) && !"POST".equals(_jspx_method) && !"HEAD".equals(_jspx_method)) {
	        response.setHeader("Allow","GET, HEAD, POST, OPTIONS");
	        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "JSPs only permit GET, POST or HEAD. Jasper also permits 			OPTIONS");
	        return;
	      }
	    }

		 final javax.servlet.jsp.PageContext pageContext; // provides convenience methods to access session, request and response info
	    javax.servlet.http.HttpSession session = null;  // session is specified in the page directive in a JSP
	    final javax.servlet.ServletContext application; // application gives the JSP access to the web application context variables
	    final javax.servlet.ServletConfig config; // config gives the JSP access to th JSP Servlet context variables
	    javax.servlet.jsp.JspWriter out = null; // out is available in all JSPs
	    final java.lang.Object page = this; // A "this" reference to the JSP Servlet object
	    javax.servlet.jsp.JspWriter _jspx_out = null;
	    javax.servlet.jsp.PageContext _jspx_page_context = null;	    

	    try {
	
	      response.setContentType("text/html;charset=UTF-8");
	      pageContext = _jspxFactory.getPageContext(this, request, response,
	      			null, true, 8192, true);
	      _jspx_page_context = pageContext;
	      application = pageContext.getServletContext();
	      config = pageContext.getServletConfig();
	      session = pageContext.getSession();
	      out = pageContext.getOut();
	      _jspx_out = out;
	
	    } catch (java.lang.Throwable t) {
	
	    }    
	    //We do not see the exception implicit variable because the above code example isErrorPage=false
  				 
#### 2. Working with example implicit variables - Greeting

From the greeting.jsp file

		<%@ page contentType="text/html;charset=UTF-8" language="java" %>
		<!-- DECLARATION-->
		<%!
		    private static final String DEFAULT_Associate = "Guest";
		%>
		<!-- SCRIPTLET -->
		<%
		    String Associate = request.getParameter("Associate");
		    if(Associate == null)
		        Associate = DEFAULT_Associate;
		%>
		<!DOCTYPE html>
		<html>
		    <head>
		        <title>Hello Associate Application</title>
		    </head>
		    <body>
		        <!-- EXPRESSION -->
		        Hello, <%= Associate %>!<br /><br />
		        <form action="greeting" method="POST">
		            Enter your name:<br />
		            <input type="text" name="Associate" /><br />
		            <input type="submit" value="Submit" />
		        </form>
		    </body>
		</html>

which is the JSP version of the HelloWorldServlet.java file but much more succinct.
We will debug and step through this JSP logic with the help of Eclipse.
We will use the input form and the query string
 
#### 3. Checkbox Form - checkboxes.jsp 
    
	<%@ page contentType="text/html;charset=UTF-8" language="java" %>
	<!DOCTYPE html>
	<html>
	    <head>
	        <title>Hello World Application</title>
	    </head>
	    <body>
	        <form action="checkboxesSubmit.jsp" method="POST">
	            Select the colors you like:<br />
	            <input type="checkbox" name="color" value="Yellow" /> Yellow<br />
	            <input type="checkbox" name="color" value="Black" /> Black<br />
	            <input type="checkbox" name="color" value="Orange" /> Orange<br />
	            <input type="checkbox" name="color" value="Red" /> Red<br />
	            <input type="checkbox" name="color" value="Blue" /> Blue<br />
	            <input type="submit" value="Submit" />
	        </form>
	    </body>
	</html>      

replicates the doGet() of the MultiValueParameterServlet
checkboxesSubmit.jsp replicates the doPost method of the MultiValueParameterServlet

	<%@ page contentType="text/html;charset=UTF-8" language="java" %>
	<%
		String[] colors = request.getParameterValues("color");
	%>
	<!DOCTYPE html>
	<html>
	    <head>
	        <title>Hello World Application</title>
	    </head>
	    <body>
	        <h2>Your Selections</h2>
	        <%
	            if(colors == null)
	            {
	        %>You did not select any colors.<%
	            }
	            else
	            {
	        %><ul><%
	                for(String color : colors)
	                {
	                    out.println("<li>" + color + "</li>");
	                }
	        %></ul><%
	            }
	        %>
	    </body>
	</html>

We will debug and step through this JSP logic with the help of Eclipse.
We will use the input form and the query string

#### 4. We look at the implicit application variable with the contextParameters.jsp

	<%@ page contentType="text/html;charset=UTF-8" language="java" %>
	<!DOCTYPE html>
	<html>
	    <head>
	        <title>Hello User Application</title>
	    </head>
	    <body>
	        settingOne: <%= application.getInitParameter("databaseOne") %>,
	        settingTwo: <%= application.getInitParameter("cloudOne") %>
	    </body>
	</html>

with the context init parameters

	<context-param>
        <param-name>databaseOne</param-name>
        <param-value>sql-server</param-value>
    </context-param>
    <context-param>
        <param-name>cloudOne</param-name>
        <param-value>google-cloud-platform</param-value>
    </context-param>
    
#### 4. The danger of using embedded JAVA in JSP

This break the Model View Controller pattern of modern back-end Java Enterprise Applications,
and locks you into a viscous cycle of catch up with every update, bug-fix and change request of your application

This is also the reason that JSP's has been superseded by Java Server Faces in the latest JEE 8
specification on the web client tier.

There also seems to be a new flavor of the month JavaScript Framework ever so often that are used
on the web tier now that the web tier is now longer just browsers but also smart-phones and the Internet
of things

##### [Embedding Java within JSP Finish Branch](https://github.com/NicorDesignsLLC/JakartaJEEWebDevelopment/tree/jee8-embedded-java-finish)

    

