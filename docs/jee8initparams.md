## Using init parameters to configure the JEE 8 Web Hello World App Module

##### [JEE 8 Init Param Start Branch](https://github.com/NicorDesigns/javawebdevcourse/tree/jee8web-init-param-start)

### 1. Using Context Init Parameters

###### Useful for setting up DB Connections, Cloud Configs etc.

###### You declare context init parameters in the web.xml deployment descriptor file
	
	<context-param>
        <param-name>databaseOne</param-name>
        <param-value>sql-server</param-value>
    </context-param>
    <context-param>
        <param-name>cloudOne</param-name>
        <param-value>google-cloud-platform</param-value>
    </context-param>
    

###### You can read in these parameters from within your servlet 

```
@WebServlet(
name = "contextParameterServlet",
urlPatterns = {"/contextParameters"}
)
public class ContextParameterServlet extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        ServletContext c = this.getServletContext();
        PrintWriter writer = response.getWriter();

        writer.append("databaseOne: ").append(c.getInitParameter("databaseOne"))
              .append(", cloudOne: ").append(c.getInitParameter("cloudOne"));
    }
}


```
			
##### Run the Web App - navigate to the Servlet URL to see the parameters output to the screen 
	
	http://localhost:8080/jee8webarchetype/contextParameters

###### These parameters are globally accessible in your web app from the point of startup 
 


### 2. Using Servlet Init Parameters
##### Because only one servlet requires the setup
###### You declare servlet init parameters in the web.xml deployment descriptor file:

	


	<servlet>
        <servlet-name>servletParameterServlet</servlet-name>
        <servlet-class>com.nicordesigns.ServletParameterServlet</servlet-class>
        <init-param>
            <param-name>database</param-name>
            <param-value>CharityAssociates</param-value>
        </init-param>
        <init-param>
            <param-name>server</param-name>
            <param-value>10.0.12.5</param-value>
        </init-param>
    </servlet>
    <servlet-mapping>
        <servlet-name>servletParameterServlet</servlet-name>
        <url-pattern>/servletParameters</url-pattern>
    </servlet-mapping>
    
##### Here you obtain the parameters from the ServletConfig object.


	public class ServletParameterServlet extends HttpServlet
	{
	    @Override
	    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException
	    {
	        ServletConfig c = this.getServletConfig();
	        PrintWriter writer = response.getWriter();
	
	        writer.append("database: ").append(c.getInitParameter("database"))
	              .append(", server: ").append(c.getInitParameter("server"));
	    }
	}	
    
### 3. Using Servlet Annotation Config Init Parameters    

##### This allows config values the be set up in running code:

https://www.baeldung.com/context-servlet-initialization-param

	@WebServlet(name = "UserServlet", urlPatterns = {"/userServlet"}, initParams={
	@WebInitParam(name="name", value="Not provided"), 
	@WebInitParam(name="email", value="Not provided")}))
	 
Check in the end git branch of this slide show 
##### [JEE 8 Init Param Finish Branch](https://github.com/NicorDesigns/javawebdevcourse/tree/jee8web-init-param-finish)

    

