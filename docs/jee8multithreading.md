## Understanding multi-threading in a JEE 8 Web App Module running in Tomcat

Relevant official Oracle Java tutorials:

[Servlets Tutorial](https://docs.oracle.com/javaee/7/tutorial/servlets012.htm)

[Concurrency Utilities Tutorial](https://docs.oracle.com/javaee/7/tutorial/concurrency-utilities002.htm)

##### [JEE 8 Multi-threading Start Branch](https://github.com/NicorDesigns/javawebdevcourse/tree/jee8web-file-upload-start)

### 1. Consider that your Web Application is dependent on the Tomcat 9 Web Server Thread Model
1. Your container maintains a Thread Pool
2. Upon receiving a web request the container hands it of to a thread from its thread pool, if and when the thread pool max limit have been reached then....
3. The web request goes into a waiting FIFO queue , once a thread becomes available the request are removed from the queue and assigned to the thread.
4.  The thread pool saves server resources by not having to create and destroy threads for every incoming request
5. The thread pool size can be configured in Tomcat
6. Once the Thread completes the Servlet Request it goes back into the Thread Pool ready to serve the next Servlet Request

### Securing variables and resources from concurrency issues such as deadlock in our code (Thread access protection)
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