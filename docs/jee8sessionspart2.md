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

In a cluster of Tomcat 9 web servers, the "sticky session" approach is used to maintain session affinity, meaning that once a user's session is assigned to a specific Tomcat server, all subsequent requests from that user will be directed to the same server. This is achieved by ensuring that the session ID is stored in the user's browser, and the load balancer routes subsequent requests based on this session ID.

Let's illustrate this with a brief example:

Assume we have two Tomcat servers, Server A and Server B, and a load balancer distributing incoming requests between the two servers.

Step 1: User's First Request
- User sends a request to the load balancer.
- The load balancer routes the request to Server A.
- Server A generates a unique session ID (e.g., "ABC123") for this user and stores it in the user's browser as a cookie.
- Server A processes the user's request and sends back the response.

Step 2: User's Subsequent Requests
- User sends another request to the load balancer.
- The load balancer reads the session ID "ABC123" from the user's browser cookie.
- The load balancer uses the session ID to determine that the user's session is associated with Server A.
- The load balancer routes the request to Server A (the same server as before).
- Server A retrieves the user's session data based on the session ID and processes the request.
- This ensures that all subsequent requests from the same user are directed to Server A as long as the session is active.

Step 3: User's Session Expiry
- If the user's session expires (e.g., due to inactivity or a specific timeout), the session data is removed from Server A.
- When the user sends a new request, the load balancer will choose either Server A or Server B, and the process starts over with a new session ID being generated.

In this way, the "sticky session" approach ensures that the user's session remains on a single server, providing session continuity and preventing issues that might arise when session data is split across multiple servers in the cluster.

Note: The term "sticky session" is commonly used, but it's worth noting that it can also be referred to as "session affinity" or "session persistence."



