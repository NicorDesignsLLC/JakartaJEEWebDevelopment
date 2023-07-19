### Maintaining State Using Sessions in our JEE 8 Web App Module

Use these (but for JEE8) to update these code examples:

[Jakarta Servlet Specification - Sessions](https://jakarta.ee/specifications/servlet/5.0/jakarta-servlet-spec-5.0.html#sessions)

We will now add a Session Activity Example in order to learn more about Session Management. It has the same index.jsp and base.jspf file as our previous example
but implements a different re-direct

##### [Maintaining State Using Sessions Part 2 Branch](https://github.com/NicorDesigns/javawebdevcourse/tree/jee8web-more-session-management-start)

#### 1. Storing more Complex data in Session (Slide 1)
##### You can store any class object in the Session however to satisfy requirements for clustering your web servers and deploying to cloud platforms the class needs to implement Serializable. 

###### We use the PageVisit Class to store our session

	public class PageVisit implements Serializable
	{
	  	  private long enteredTimestamp; //Allow for null value
	
	     private Long leftTimestamp;
	
	     private String request;
	
	     private InetAddress ipAddress;
	 }	

###### We use the ActivityServlet Class to update our session


	@WebServlet(
        name = "activitySessionServlet",
        urlPatterns = "/activitySession"
	)
	public class ActivityServlet extends HttpServlet
	{
    
		@Override
	    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException
	    {
	        this.recordSessionActivity(request);
	
	        this.viewSessionActivity(request, response);
	    }
	
	    private void recordSessionActivity(HttpServletRequest request)
	    {
	        //Retrieve the session
	        HttpSession session = request.getSession();
	
			 //Creates Activity Vector if it does not exist	
	        if(session.getAttribute("activity") == null)
	            session.setAttribute("activity", new Vector<PageVisit>());
	        
	        //Replace with https://www.baeldung.com/java-copy-on-write-arraylist
	        //Updates the lefttimestamp of the last visit
	        @SuppressWarnings("unchecked")
	        Vector<PageVisit> visits =
	                (Vector<PageVisit>)session.getAttribute("activity");
	
	        if(!visits.isEmpty())
	        {
	            PageVisit last = visits.lastElement();
	            last.setLeftTimestamp(System.currentTimeMillis());
	        }
	
	        //Adds more information to the current Vector
	        PageVisit now = new PageVisit();
	        now.setEnteredTimestamp(System.currentTimeMillis());
	        if(request.getQueryString() == null)
	            now.setRequest(request.getRequestURL().toString());
	        else
	            now.setRequest(request.getRequestURL()+"?"+request.getQueryString());
	        try
	        {
	            now.setIpAddress(InetAddress.getByName(request.getRemoteAddr()));
	        }
	        catch (UnknownHostException e)
	        {
	            e.printStackTrace();
	        }
	        visits.add(now);
	    }
	
	    private void viewSessionActivity(HttpServletRequest request,
	                                     HttpServletResponse response)
	            throws ServletException, IOException
	    {
	    	 //forwards to a JSP	
	        request.getRequestDispatcher("/WEB-INF/jsp/view/viewSessionActivity.jsp")
	               .forward(request, response);
	    }
	}
			
###### We use the viewSessionActivity.jsp to display our session

	<%@ page import="java.util.Vector, com.nicordesigns.PageVisit, java.util.Date" %>
	<%@ page import="java.text.SimpleDateFormat" %>
	<%!
	    private static String toString(long timeInterval)
	    {
	        if(timeInterval < 1_000)
	            return "less than one second";
	        if(timeInterval < 60_000)
	            return (timeInterval / 1_000) + " seconds";
	        return "about " + (timeInterval / 60_000) + " minutes";
	    }
	%>
	<%
	    SimpleDateFormat f = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z");
	%>
	<!DOCTYPE html>
	<html>
	    <head>
	        <title>Session Activity Tracker</title>
	    </head>
	    <body>
	        <h2>Session Properties</h2>
	        Session ID: <%= session.getId() %><br />
	        Session is new: <%= session.isNew() %><br />
	        Session created: <%= f.format(new Date(session.getCreationTime()))%><br />
	
	        <h2>Page Activity This Session</h2>
	        <%
	            @SuppressWarnings("unchecked")
	            Vector<PageVisit> visits =
	                    (Vector<PageVisit>)session.getAttribute("activity");
	
	            for(PageVisit visit : visits)
	            {
	                out.print(visit.getRequest());
	                if(visit.getIpAddress() != null)
	                    out.print(" from IP " + visit.getIpAddress().getHostAddress());
	                out.print(" (" + f.format(new Date(visit.getEnteredTimestamp())));
	                if(visit.getLeftTimestamp() != null)
	                {
	                    out.print(", stayed for " + toString(
	                            visit.getLeftTimestamp() - visit.getEnteredTimestamp()
	                    ));
	                }
	                out.println(")<br />");
	            }
	        %>
	    </body>
	</html>
	
##### 2. Compiling, testing and debugging our session-activity web application
Add different paths and query parameters to the URL replace /home but leave do

##### 3. Applying Sessions in our charity-registration module
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

##### 4. Compiling, testing and debugging our session-registration web application

Use incorrect user name and password
Use valid user name and password
Add some Registrations and look at them
Close the browser and re-open and re-visit Registration creation 

##### 5. Add a logout link

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

##### 6. Detecting Changes to Sessions Using Listeners

There are several listeners defined in the Servlet API which listens to some form of session activity, you subscribe events by implementing a listener interface by adding a Listener configuration to your app. You can do this programmatically of through annotations and the web.xml configuration file

When something happens that triggers the publication of an Event to which your code is subscribed, the JEE container invokes the corresponding method.

Examples of the Listener Interfaces are :

[httpsessionattributelistener](https://jakarta.ee/specifications/servlet/4.0/apidocs/javax/servlet/http/httpsessionattributelistener)

[httpsessionbindinglistener](https://jakarta.ee/specifications/servlet/4.0/apidocs/javax/servlet/http/httpsessionbindinglistener)

[httpsessionidlistener](https://jakarta.ee/specifications/servlet/4.0/apidocs/javax/servlet/http/httpsessionidlistener)

[httpsessionlistener](https://jakarta.ee/specifications/servlet/4.0/apidocs/javax/servlet/http/httpsessionlistener)

We implement the last two in the SessionListener.java class

		@WebListener
		public class SessionListener implements HttpSessionListener, HttpSessionIdListener
		{
		
		 	private SimpleDateFormat formatter =
            new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");

		    @Override
		    public void sessionCreated(HttpSessionEvent e)
		    {
		        System.out.println(this.date() + ": Session " + e.getSession().getId() +
		                " created.");
		        SessionRegistry.addSession(e.getSession());
		    }
		
		    @Override
		    public void sessionDestroyed(HttpSessionEvent e)
		    {
		        System.out.println(this.date() + ": Session " + e.getSession().getId() +
		                " destroyed.");
		        SessionRegistry.removeSession(e.getSession());
		    }
		
		    @Override
		    public void sessionIdChanged(HttpSessionEvent e, String oldSessionId)
		    {
		        System.out.println(this.date() + ": Session ID " + oldSessionId +
		                " changed to " + e.getSession().getId());
		        SessionRegistry.updateSessionId(e.getSession(), oldSessionId);
		    }
		
		    private String date()
		    {
		        return this.formatter.format(new Date());
		    }

		}
		
We will now run and debug our web application and look at the Console output of the System.out.println()
methods in the class above.

##### 7. Maintaining a List of Active Sessions

We use our own implementation of the  

[httpsessionlistener](https://jakarta.ee/specifications/servlet/4.0/apidocs/javax/servlet/http/httpsessionlistener) and [httpsessionidlistener](https://jakarta.ee/specifications/servlet/4.0/apidocs/javax/servlet/http/httpsessionidlistener) classes, in order to implement a Listener Interface class: 
[SessionListener.java](https://github.com/NicorDesigns/javawebdevcourse/blob/jee8web-more-session-management-finish/charity-registration/src/main/java/com/nicordesigns/SessionListener.java)

To keep an in memory database in our Charity Registration Application:

We create the following class:
[InMemorySessionDB.java](https://github.com/NicorDesigns/javawebdevcourse/blob/jee8web-more-session-management-finish/charity-registration/src/main/java/com/nicordesigns/InMemorySessionDB.java)
This class stores all the Heap References to all the created Session Objects


In our SessionListener Class we add, remove and update the Session References to the In Memory Database when the Sessions are created, destroyed or updated.
[SessionListener.java](https://github.com/NicorDesigns/javawebdevcourse/blob/jee8web-more-session-management-finish/charity-registration/src/main/java/com/nicordesigns/SessionListener.java)

We create a Servlet to display the list of Sessions:

[SessionListServlet.java](https://github.com/NicorDesigns/javawebdevcourse/blob/jee8web-more-session-management-finish/charity-registration/src/main/java/com/nicordesigns/SessionListServlet.java)

and a sessions.jsp to display these Sessions.

[sessions.jsp](https://github.com/NicorDesigns/javawebdevcourse/blob/jee8web-more-session-management-finish/charity-registration/src/main/webapp/WEB-INF/jsp/view/sessions.jsp)

Check in the end Git branch of this slide show 

##### [More Maintaining State Using Sessions Finish Branch](https://github.com/NicorDesigns/javawebdevcourse/tree/jee8web-more-session-management-finish)

    

