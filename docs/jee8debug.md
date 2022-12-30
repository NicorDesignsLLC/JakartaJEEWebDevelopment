## Debugging the JEE 8 Web Hello World Servlet module

#### [JEE 8 Hello World Debug Start Branch](https://github.com/NicorDesigns/javawebdevcourse/tree/jee8web-debug-start)

### 1. In Eclipse select Window -> Preferences -> Server -> Runtime Environments -> Select Tomcat 9 and Edit

###### Ensure that your Tomcat 9 server is set up correctly

### 2. Now add in-line java code and a debug point to the JSP page

##### Run the Web App in Debug mode

##### This will stop at the JSP line to debug and open the Eclipse Debug Perspective where you can track the stack 

### 4. Repeat the process with the debug point in the servlet

##### This will stop at the servlet line to debug and open the Eclipse Debug Perspective where you can track the stack 

### 4. Add basic logging to the JEE 8 Web App

##### [Apache Log4J 2](https://logging.apache.org/log4j/2.x/index.html)

##### Add Maven Dependencies
	
	<dependency>
    	<groupId>org.apache.logging.log4j</groupId>
    	<artifactId>log4j-api</artifactId>
    	<version>2.17.0</version>
	</dependency>
	<dependency>
	    <groupId>org.apache.logging.log4j</groupId>
	    <artifactId>log4j-core</artifactId>
	    <version>2.17.0</version>
	</dependency>
	
##### Add log4j.properties
	
	appenders=xyz
	appender.xyz.type=Console
	appender.xyz.name=myOutput
	appender.xyz.layout.type=PatternLayout
	appender.xyz.layout.pattern=[%d{yy-MMM-dd HH:mm:ss:SSS}] [%p] [%c{1}:%L] - %m%n
	rootLogger.level=debug
	rootLogger.appenderRefs=abc
	rootLogger.appenderRef.abc.ref=myOutput

	
##### Add debugging code to the Servlet

	private static Logger LOGGER = LogManager.getLogger(HelloWorldServlet.class);
	
	LOGGER.info("Servlet " + this.getServletName() + " has started.");
	
	LOGGER.info("Servlet " + this.getServletName() + " has stopped.");	


##### This will allow you to log status and errors 
 

Check in the end git branch of this slide show 
#### [JEE 8 Hello World Servlet Finish Branch](https://github.com/NicorDesigns/javawebdevcourse/tree/jee8web-servlet-finish)

    

