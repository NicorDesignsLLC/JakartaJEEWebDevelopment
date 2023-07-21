### Maintaining State Using Sessions in our JEE 8 Web App Module

Use these (but for JEE8) to update these code examples:

[Jakarta Servlet Specification - Sessions](https://jakarta.ee/specifications/servlet/5.0/jakarta-servlet-spec-5.0.html#sessions)

In a cluster of Tomcat 9 web servers, the "sticky session" approach is used to maintain session affinity, meaning that once a user's session is assigned to a specific Tomcat server, all subsequent requests from that user will be directed to the same server. This is achieved by ensuring that the session ID is stored in the user's browser, and the load balancer routes subsequent requests based on this session ID.

#### Let's illustrate this with a brief example:

Assume we have two Tomcat servers, Server A and Server B, and a load balancer distributing incoming requests between the two servers.

#### Step 1: User's First Request
- User sends a request to the load balancer.
- The load balancer routes the request to Server A.
- Server A generates a unique session ID (e.g., "ABC123") for this user and stores it in the user's browser as a cookie.
- Server A processes the user's request and sends back the response.

#### Step 2: User's Subsequent Requests
- User sends another request to the load balancer.
- The load balancer reads the session ID "ABC123" from the user's browser cookie.
- The load balancer uses the session ID to determine that the user's session is associated with Server A.
- The load balancer routes the request to Server A (the same server as before).
- Server A retrieves the user's session data based on the session ID and processes the request.
- This ensures that all subsequent requests from the same user are directed to Server A as long as the session is active.

#### Step 3: User's Session Expiry
- If the user's session expires (e.g., due to inactivity or a specific timeout), the session data is removed from Server A.
- When the user sends a new request, the load balancer will choose either Server A or Server B, and the process starts over with a new session ID being generated.

In this way, the "sticky session" approach ensures that the user's session remains on a single server, providing session continuity and preventing issues that might arise when session data is split across multiple servers in the cluster.

Note: The term "sticky session" is commonly used, but it's worth noting that it can also be referred to as "session affinity" or "session persistence."


#####Setting up a two-node Tomcat 9 cluster on a single Windows machine requires some configuration, and it's important to note that this setup is for demonstration purposes only and not recommended for production use. For production environments, you should use separate physical or virtual machines for each node.

Before starting, make sure you have Apache Tomcat 9 installed on your machine and that you have set the environment variable `CATALINA_HOME` to the Tomcat installation directory.

Here's a simple example to demonstrate a two-node Tomcat 9 cluster on a Windows 10/11 machine:

#### Step 1: Prepare the Environment

1. Create two Tomcat instances by copying your original Tomcat directory to two different locations. For example, let's call them `tomcat-node1` and `tomcat-node2`.

2. Update the HTTP connector port for each instance, so they don't conflict. For instance, you can change the port in the `conf/server.xml` file for `tomcat-node1` to 8081 and `tomcat-node2` to 8082.

#### Step 2: Configure the Cluster

1. Open the `conf/server.xml` file for each Tomcat instance.

2. Add the following cluster configuration inside the `<Engine>` element for both `tomcat-node1` and `tomcat-node2`:

```xml
<Cluster className="org.apache.catalina.ha.tcp.SimpleTcpCluster" 
         channelSendOptions="8">
    <Manager className="org.apache.catalina.ha.session.DeltaManager"
             expireSessionsOnShutdown="false"
             notifyListenersOnReplication="true"/>
    <Channel className="org.apache.catalina.tribes.group.GroupChannel">
        <Membership className="org.apache.catalina.tribes.membership.McastService"
                    address="228.0.0.4"
                    port="45564"
                    frequency="500"
                    dropTime="3000"/>
        <Receiver className="org.apache.catalina.tribes.transport.nio.NioReceiver"
                  address="auto"
                  port="4000"
                  autoBind="100"
                  selectorTimeout="5000"
                  maxThreads="6"/>
        <Sender className="org.apache.catalina.tribes.transport.ReplicationTransmitter">
            <Transport className="org.apache.catalina.tribes.transport.nio.PooledParallelSender"/>
        </Sender>
        <Interceptor className="org.apache.catalina.tribes.group.interceptors.TcpFailureDetector"/>
        <Interceptor className="org.apache.catalina.tribes.group.interceptors.MessageDispatch15Interceptor"/>
    </Channel>
</Cluster>
```

3. Save the changes and close the files.

#### Step 3: Start the Cluster Nodes

1. Open a command prompt and navigate to `tomcat-node1\bin` folder.

2. Start the first node by running: `startup.bat`

3. Open another command prompt and navigate to `tomcat-node2\bin` folder.

4. Start the second node by running: `startup.bat`

#### Step 4: Deploy a Sample Web Application

1. Create a simple web application (e.g., a WAR file) or use the Tomcat sample application (`tomcat\webapps\ROOT`) for testing.

2. Copy the web application (WAR file) to the `webapps` folder of both Tomcat instances.

#### Step 5: Test the Cluster

1. Open a web browser and access the web application using the following URLs:

   - Node 1: http://localhost:8081/your-web-app
   - Node 2: http://localhost:8082/your-web-app

2. Verify that both nodes show the same content, indicating that the cluster is working correctly.

Remember that this setup is only for demonstration purposes on a single machine and not suitable for production use due to resource limitations. In a real production environment, you should deploy the nodes on separate machines to achieve high availability and avoid single points of failure.




