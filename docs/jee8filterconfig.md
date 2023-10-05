# Creating, Declaring and Mapping Filters

We create a Filter by implementing the [Jakarta Filter](https://jakarta.ee/specifications/platform/8/apidocs/javax/servlet/filter) interface. 

From the docs
 - init
 - destroy
 - doFilter

In our case we will always use [httpfilter](https://jakarta.ee/specifications/platform/8/apidocs/javax/servlet/http/httpfilter) which is available from the Servlet 4.0 definition and is Filter -> GenericFilter -> HttpFilter

The doFilter [doc here ](https://jakarta.ee/specifications/platform/8/apidocs/javax/servlet/filter) explains how we will be coding our implementation


## Understanding the Filter Chain

[Filter Chain](https://jakarta.ee/specifications/platform/8/apidocs/javax/servlet/filterchain)

Actually I do have a filter chain example in one of my Struts 2 Slides of which I can re-use the graphic here:

[Filter Chain Example](https://o7planning.org/10395/java-servlet-filter)

[Filter Chain Slide Example](https://docs.google.com/presentation/d/14oqweqUjTYBnBvfSrKBhi3RtVVhcMXRL7RZPZeMszD8/edit#slide=id.g41c9f0eb7b_2_466)

This reminds me that when I revisit the Struts 2 presentation to move the code to a Github Repo and to create a Markdown Github Page for that lesson that I should also put all my Struts 2 slides together into on single slideshow presentation so that I can manage everything better. 

The filterchain graphic / slide will show how all filters will be processed using
[Filter Chain](https://jakarta.ee/specifications/platform/8/apidocs/javax/servlet/filterchain)
				FilterChain.doFilter() 
Passed from filter to filter until all is processed and then passed on to the Servlet
If this does not get called then the Filter Chain is interrupted.

The underlying tech used is off course the [Java Stack : ](https://www.baeldung.com/java-stack) which means a filter can take action on both incoming and outgoing requests (as demonstrated in the slide)

### Mapping to URL Patterns and Servlet Names

Just like you map your Servlets either in the web.xml or with annotations inside the Java Code you can also map your Filters.

You use a URL pattern that will ensure that any request that matches the pattern will be intercepted by the filter before it reaches the Servlet or for that matter any other resource such as images, javascript files etc.

You can also map filters to a specific Servlet Name that will ensure that all request to the Servlet will pass through all the filter or plural filters that you have defined.

A single filter can intercept more than one URL pattern or Servlet and a single URL pattern or Servlet can have more than one Filters defined as interceptors

### Mapping to Different Request Dispatcher types

[RequestDispatcher](https://javadoc.io/static/jakarta.servlet/jakarta.servlet-api/5.0.0/jakarta/servlet/RequestDispatcher.html)

Error Requests - these are request to Error Handling Resources such as custom jsp error pages

Forward Requests - a seperate internal request to forward to a different resource as in <jsp:forward>

Include Requests - Internal Requests related to the original request as in <jsp:include>

Normal Reqquests - URL maps to the specific app running in the container (Tomcat 9 in our case)

Then there is the [Async Request handler](https://jakarta.ee/specifications/platform/9/apidocs/jakarta/servlet/asynccontext)


### Using the Deployment Descriptor


Inside web.xml 

		<filter>
			<filter-name>myFilter</filter-name>
			<filter-class>com.nicordesigns.MyFilter</filter-class>
		</filter>

The fileter init method is always called on server startup, and in the order they are defined.

Mapping the filter

		<filter-mapping>
			<filter-name>myFilter</filter-name>
			<url-pattern>/foo</url-pattern>
			<url-pattern>/bar/*</url-pattern>
			<servlet-name>myServlet</servlet-name>
			<dispatcher>REQUEST</dispatcher>
			<dispatcher>ASYNC<dispatcher>
		</filter-mapping>

As you can see you can map it to X url patterns or servlet names. These patterns can also include wildcards. This works the same way as for Servlets.

The 2 dispatcher request should be familiar after we explained it above and we looked at the official documentation

### Using Annotations

We use the WebFilter annotation for all the attributes that were contained in our XML configuration example

https://jakarta.ee/specifications/servlet/4.0/apidocs/index.html?javax/servlet/annotation/WebFilter.html


		@WebFilter(
				filterName = "myFilter",
				urlPatterns = {"/foo", "/bar/**"}),
				servletNames = {"myServlet"}, 
				dispatcherTypes = {DispatcherType.REQUEST, DispatcherType.ASYNC}
				
		public class MyFilter implements Filter

When using annotations we lose the ability to set the order of the filters on a filter chain (check if this is still true for Servlet 4.0 - JEE8 ? Seems to be) to be able to do this we need


### Programmatic Configuration of Filters

By calling methods on 

https://jakarta.ee/specifications/servlet/4.0/apidocs/javax/servlet/servletcontext

you can register and map filters

We have to do this before the ServletContext finish intitializing, therefore we use the

ServletContextListener's contextInitialized method 

	@WebListener
	public class Configurator implements ServletContextListener
	{
		@Override
		public void contextInitialized(ServletContextEvent event)
		{
			ServletContext context = event.getServletContext();
	
			FilterRegistration.Dynamic registration = context.addFilter(
					"testFilter", new TestFilter()
			);
						        registration.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST, DispatcherType.ASYNC),
					                false, "/foo", "/bar/*"
					        );
					        registration.addMappingForServletNames(EnumSet.of(DispatcherType.REQUEST, DispatcherType.ASYNC),
					                false, "myServlet
					        );
		
		}
	
		@Override
		public void contextDestroyed(ServletContextEvent event) { }
	}

In this code example we add the filter to the ServletContext with addFilter() which returns a FilterRegistration.Dynamic which we use to add filter mappings for URL patterns and Servlet names. 

			addMappingForUrlPatterns()
			addMappingForServletNames()

both accept a Set of DispatcherTypes for the first argument, if it is null then the default REQUEST DispatcherType is assumed

		registration.addMappingForUrlPatterns(null, 
							                false, "/foo", "/bar/*"
							        );

the second method argument indicates the filter's order relative to the filters in the deployment descriptor. If false they are ordered before any mappings in the deployment descriptor, if true they are ordered after any mappings in the deployment descriptor. The final parameter(s) are a vararg for the URL patterns or Servlet names. 
