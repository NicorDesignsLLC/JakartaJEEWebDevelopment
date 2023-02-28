Relevant official Oracle Java tutorials:

[Asynchronous Processing](https://docs.oracle.com/javaee/7/tutorial/servlets012.htm)

[Main Components of the Concurrency Utilities](https://docs.oracle.com/javaee/7/tutorial/concurrency-utilities002.htm)

##### [JEE 8 Multi-threading Start Branch](https://github.com/NicorDesigns/javawebdevcourse/tree/jee8web-file-upload-start)

### 1. Consider that your Web Application is dependent on the Tomcat 9 Web Server Thread Model
1. Your container maintains a Thread Pool
2. Upon receiving a web request the container hands it of to a thread from its thread pool, if and when the thread pool max limit have been reached then....
3. The web request goes into a waiting FIFO queue , once a thread becomes available the request are removed from the queue and assigned to the thread.
4.  The thread pool saves server resources by not having to create and destroy threads for every incoming request
5. The thread pool size can be configured in Tomcat
6. Once the Thread completes the Servlet Request it goes back into the Thread Pool ready to serve the next Servlet Request

### Re-visiting our Sample User Story

#### As an expatriate living abroad I want to create an online database of all charities (non-profits) that operate in my “home country”.

1. Our example administration web-app will allow charities to "register" with our online DB and send us their official tax registration forms

2. Charities should be able to register their information online and provide attached files such as non-profit tax registration status and address details

3. Our specific example will have to use i8n and localization for specific language groups and regions

Because we handle sensitive information our project will have to be really secure

#### Our final Actionable Item

* Design a database that conforms to User Story

* Build this database with SQL Scripts

* <b>Build the Jakarta JEE 8 Web Application to allow the administration of the Charity Database</b>


Create a Maven Archetype web -app and upgrade it to match JEE 8
(Generate Interactively Demo)
<dependency>
    <groupId>org.apache.maven.archetypes</groupId>
    <artifactId>maven-archetype-webapp</artifactId>
    <version>1.4</version>
</dependency>

Unfortunately as we see the Maven Archetype has not been updated for the latest JEE Web App Specification(s) and now we will have to manually move the generated web app to Jakarta JEE 8 using Java 11

Let me know in the comments if you can find the latest Maven Archetypes..or since it is Open Source this is a good opprtunity for aspiring volunteer open source developers to write all of those and put it out there with your name on it!

Fortunately we have our jee8webapp to us as a guiding template and we will start with updating the generated pom.xml first..

Compare the two pom.xml files with each other in Eclipse (and update).
Use Eclipse Maven Update

Compare the two web.xml config files in Eclipse (and update)

Add src/main/java folder & src/main/test folder

Add FileAttachment and Registration java pojo classes

Add RegistrationServlet.java

Add javax.servlet.jsp-api and javax.servlet.jsp.jstl to the maven pom.xml file

Change index page to be able to redirect to /registrations



### Securing variables and resources from concurrency issues such as deadlock in our code (Thread access protection)

[Synchronization](https://docs.oracle.com/javase/tutorial/essential/concurrency/sync.html)

Using volatile and synchronized code blocks only allows one thread access and update at a time

		//Ensure single thread access
		private volatile int REGISTRATION_ID_SEQUENCE = 1;
		
		int id;
		
		synchronized(this)
		{
			id = this.REGISTRATION_ID_SEQUENCE++;
			this.registrationDatabase.put(id, registration);
		}

Check in the end git branch of this slide show

##### [JEE 8 Multi-threading Finish Branch](https://github.com/NicorDesigns/javawebdevcourse/tree/jee8web-multithreading-end)