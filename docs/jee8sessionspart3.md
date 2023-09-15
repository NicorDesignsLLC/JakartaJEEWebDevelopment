### Maintaining State Using Sessions in our Charity Registration JEE 8 Web App Module


[Jakarta Servlet Specification - Sessions](https://jakarta.ee/specifications/servlet/5.0/jakarta-servlet-spec-5.0.html#sessions)

##### 1. Detecting Changes to Sessions Using Listeners

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

##### 2. Maintaining a List of Active Sessions

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

    

