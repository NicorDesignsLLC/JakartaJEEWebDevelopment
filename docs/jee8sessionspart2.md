### Maintaining State Using Sessions in our JEE 8 Web App Module

[Jakarta Servlet Specification - Sessions](https://jakarta.ee/specifications/servlet/5.0/jakarta-servlet-spec-5.0.html#sessions)

We will now add a Session Activity Example in order to learn more about Session Management.
 
It has the same index.jsp and base.jspf file as our previous example but implements a different re-direct

##### [Maintaining State Using Sessions Part 2 Branch](https://github.com/NicorDesigns/javawebdevcourse/tree/jee8web-more-session-management-start)

#### 1. Storing more Complex data in Session
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
	public class ActivityServlet extends HttpServlet {
	    private static final long serialVersionUID = -6775967155118931064L;
	
	    @Override
	    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
	        this.recordSessionActivity(request);
	
	        this.viewSessionActivity(request, response);
	    }
	
	    private void recordSessionActivity(HttpServletRequest request) {
	        HttpSession session = request.getSession();
	
	        if (session.getAttribute("activity") == null)
	            session.setAttribute("activity", new ArrayList<PageVisit>());
	
	        @SuppressWarnings("unchecked")
	        List<PageVisit> visits =
	                (List<PageVisit>) session.getAttribute("activity");
	
	        if (!visits.isEmpty()) {
	            PageVisit last = visits.get(visits.size() - 1);
	            last.setLeftTimestamp(System.currentTimeMillis());
	        }
	
	        PageVisit now = new PageVisit();
	        now.setEnteredTimestamp(System.currentTimeMillis());
	        if (request.getQueryString() == null)
	            now.setRequest(request.getRequestURL().toString());
	        else
	            now.setRequest(request.getRequestURL() + "?" + request.getQueryString());
	        try {
	            now.setIpAddress(InetAddress.getByName(request.getRemoteAddr()));
	        } catch (UnknownHostException e) {
	            e.printStackTrace();
	        }
	        visits.add(now);
	    }
	
	    private void viewSessionActivity(HttpServletRequest request,
	                                     HttpServletResponse response)
	            throws ServletException, IOException {
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

We will demonstrate that different browsers each get assigned a different session id and associated session object by running our sample session object.

