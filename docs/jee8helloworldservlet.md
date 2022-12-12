# Updating the JEE 8 Web Application Module with a Hello World Servlet

#### [JEE 8 Hello World Servlet Start Branch](https://github.com/NicorDesignsLLC/JakartaJEEWebDevelopment/commits/hello-world-servlet-jee8-start)

### 1. Add the actual servlet code

###### com.nicordesigns.HelloWorldServlet extends HttpServlet which in turns extend GenericServlet, Servlet

##### From the JEE 8 Sevlet API doc: 

##### A servlet is a small Java program that runs within a Web server. Servlets receive and respond to requests from Web clients, usually across HTTP, the HyperText Transfer Protocol.
##### This HttpServlet & GenericServlet interface defines methods to initialize a servlet, to service requests, and to remove a servlet from the server. These are known as life-cycle methods and are called in the following sequence:

1. The servlet is constructed, then initialized with the init method.
2. Any calls from clients to the service method are handled.
3. The servlet is taken out of service, then destroyed with the destroy method, then garbage collected and finalized.
4. In addition to the life-cycle methods, this interface provides the getServletConfig method, which the servlet can use to get any startup information, and the getServletInfo method, which allows the servlet to return basic information about itself, such as author, version, and copyright.

##### We will look at all the methods in HttpServlet with the help of Eclipse Source browser - doGet, doPost etc.
Creating a Servlet Class
					
Servlet inherets from GenericServlet, protocol independent with a single service method

Http Request will be handled by the HttpServlet and the overriden HTTP Protocol methods
						
How the code in HelloWorld Servlet works 

Calling getWriter on response parameter returns a Printwriter then using println() to output text to the output stream
						
##### We will also look at the init() and destroy() methods by overriding them

init() sometimes runs when the servlet container starts up the web app (can be configured) otherwise when the servlet are running or responding on it very first run - so we use it to set up resources like database connections etc

destroy() runs when the servlet Container shuts down - use it to clean up resource like database connections etc - important to use this and not the garbage collection code   

### 2. Add the servlet config to web.xml

##### com.nicordesigns.HelloWorldServlet

	<servlet>
		<servlet-name>helloServlet</servlet-name>
		<servlet-class>com.nicordesigns.HelloWorldServlet</servlet-class>
	</servlet>

	<servlet-mapping>
		<servlet-name>helloServlet</servlet-name>
		<url-pattern>/hello-world</url-pattern>
	</servlet-mapping>


#####  

### 4. Demonstrate that the Hello World Servlet works


Check in the end git branch of this slide show 
#### [JEE 8 Hello World Servlet Finish Branch](https://github.com/NicorDesignsLLC/JakartaJEEWebDevelopment/commits/hello-world-servlet-jee8-end)

