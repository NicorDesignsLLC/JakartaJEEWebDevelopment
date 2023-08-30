### Maintaining State Using Sessions in our JEE 8 Web App Module

Use these (but for JEE8) to update these code examples:

[Jakarta Servlet Specification - Sessions](https://jakarta.ee/specifications/servlet/5.0/jakarta-servlet-spec-5.0.html#sessions)


##### 1. Applying Sessions in our charity-registration module
We add the <session-config> entry to our web.xml and and an in memory database to store Users and a login page and servlet

###### We add a login to the charity-registration app:
We remove session=false in all the JSP's and add the <session-config> to our web.xml in order to use cookies 

###### Our login servlet

	@WebServlet(
        name = "loginServlet",
        urlPatterns = "/login"
	)
	public class LoginServlet extends HttpServlet
	{
	    private static final long serialVersionUID = 1L;
		
		private static final Map<String, String> userDatabase = new Hashtable<>();
	
		//Simple in memory DB to store users and passwords - no encryption
	    static {
	        userDatabase.put("Nicolaas", "password");
	        userDatabase.put("Sarah", "drowssap");
	        userDatabase.put("Mike", "wordpass");
	        userDatabase.put("John", "green");
	    }
	
	    //Here we re-direct to the login.jsp page
	    @Override
	    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException
	    {
	        HttpSession session = request.getSession();
	        
	      //Check if the User is already logged in
	        if(request.getParameter("logout") != null)
	        {
	        	//Send to login page if not
	            session.invalidate();
	            response.sendRedirect("login");
	            return;
	        }
	        else if(session.getAttribute("username") != null) 
	        {
	        	//Send to application if logged in
	        	session.setAttribute("username", session.getAttribute("username").toString());
	            response.sendRedirect("registrations");
	            return;
	        }
	
	        request.setAttribute("loginFailed", false);
	        
	        //Redirect to the login page
	        request.getRequestDispatcher("/WEB-INF/jsp/view/login.jsp")
	               .forward(request, response);
	    }
	
	    //The login.jsp for POSTs to this method
	    @Override
	    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException
	    {
	    	
	        HttpSession session = request.getSession();
	        if(session.getAttribute("username") != null) //User is alread logged in and in session
	        {
	            response.sendRedirect("registrations");
	            return;
	        }
	
	        String username = request.getParameter("username");
	        String password = request.getParameter("password");
	        
	        //Forward to application if the login is successful
	        if(username == null || password == null ||
	                !LoginServlet.userDatabase.containsKey(username) ||
	                !password.equals(LoginServlet.userDatabase.get(username)))
	        {
	            
	        	request.setAttribute("loginFailed", true); //Use this attribute to indicate login failure
	            
	            request.getRequestDispatcher("/WEB-INF/jsp/view/login.jsp")
	                   .forward(request, response);
	        }
	        else
	        {
	            request.changeSessionId();
	            session.setAttribute("username", username);
	            response.sendRedirect("registrations");
	        }
	    }
	}
	
###### Our login JSP

		<!DOCTYPE html>
		<html>
		    <head>
		        <title>Charity Associate Support</title>
		    </head>
		    <body>
		        <h2>Login</h2>
		        You must log in to access the charity registration site.<br /><br />
		        <!-- On login failed returned from the login Servlet -->
		        <%
		            
		            if(((Boolean)request.getAttribute("loginFailed")))
		            {
		                %>
		        <b>The user name or password you entered are not correct. Please try
		            again.</b><br /><br />
		                <%
		            }
		        %>
		        <form method="POST" action="<c:url value="/login" />">
		            User name<br />
		            <input type="text" name="username" /><br /><br />
		            Password<br />
		            <input type="password" name="password" /><br /><br />
		            <input type="submit" value="Log In" />
		        </form>
		    </body>
		</html>
		
We can now use the user name to populate our Registration object from the session after the User has logged in

##### 2. Compiling, testing and debugging our session-registration web application

Use incorrect user name and password
Use valid user name and password
Add some Registrations and look at them
Close the browser and re-open and re-visit Registration creation 

##### 3. Add a logout link

Add code to the LoginServlet doGet() method

		 //Check if the User is already logged in
        if(request.getParameter("logout") != null)
        {
        	//Send to login page if not
            session.invalidate();
            response.sendRedirect("login");
            return;
        }
        else if(session.getAttribute("username") != null) 
        {
        	//Send to application if logged in
        	session.setAttribute("username", session.getAttribute("username").toString());
            response.sendRedirect("registrations");
            return;
        }
Add a logout link at the top of the JSPs

		<a href="<c:url value="/login?logout" />">Logout</a>

###### Repeat the testing and debugging steps as above

