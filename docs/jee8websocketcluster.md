# 3. Using WebSockets to communicate in a Cluster

In this section, we will explore how to leverage WebSockets for communication within a clustered environment using Apache Tomcat. The scenario involves two WebSocket endpoints deployed on separate cluster nodes that communicate with each other through a servlet.

## Setting Up the Servlet Configuration

To start, we need to configure our `web.xml` to define servlets for each cluster node. Add the following configuration to your `web.xml`:

```xml
<servlet>
    <servlet-name>clusterNodeA</servlet-name>
    <servlet-class>com.nicordesigns.servlets.ClusterNodeServlet</servlet-class>
    <init-param>
        <param-name>nodeId</param-name>
        <param-value>A</param-value>
    </init-param>
</servlet>

<servlet-mapping>
    <servlet-name>clusterNodeA</servlet-name>
    <url-pattern>/clusterNodeA</url-pattern>
</servlet-mapping>

<servlet>
    <servlet-name>clusterNodeB</servlet-name>
    <servlet-class>com.nicordesigns.servlets.ClusterNodeServlet</servlet-class>
    <init-param>
        <param-name>nodeId</param-name>
        <param-value>B</param-value>
    </init-param>
</servlet>

<servlet-mapping>
    <servlet-name>clusterNodeB</servlet-name>
    <url-pattern>/clusterNodeB</url-pattern>
</servlet-mapping>
```

This configuration defines two servlets (`clusterNodeA` and `clusterNodeB`) mapped to different URL patterns.

## WebSocket Endpoint Implementation

Next, let's examine the WebSocket endpoint implementation in the `ClusteredChatServerNodeEndPoint` class. This class manages WebSocket connections and facilitates communication between cluster nodes.

```java
@ServerEndpoint("/charityChat/{nodeId}")
public class ClusteredChatServerNodeEndPoint {
   private static final List<Session> nodes = new ArrayList<>(2);

    @OnOpen
    public void onOpen(Session session, @PathParam("nodeId") String nodeId) {
        System.out.println("INFO: Node [" + nodeId + "] connected to cluster.");
        ClusterMessage message = new ClusterMessage(nodeId, "Joined the cluster.");
        try
        {
            byte[] bytes = ClusteredChatServerNodeEndPoint.toByteArray(message);
            for(Session node : ClusteredChatServerNodeEndPoint.nodes)
                node.getBasicRemote().sendBinary(ByteBuffer.wrap(bytes));
        }
        catch(IOException e)
        {
            System.err.println("ERROR: Exception when notifying of new node");
            e.printStackTrace();
        }
        ClusteredChatServerNodeEndPoint.nodes.add(session);

    }

    @OnClose
    public void onClose(Session session, @PathParam("nodeId") String nodeId)
    {
    	 System.out.println("INFO: Node [" + nodeId + "] disconnected.");

    	 ClusteredChatServerNodeEndPoint.nodes.remove(session);

         ClusterMessage message = new ClusterMessage(nodeId, "Left the cluster.");
         try
         {
             byte[] bytes = ClusteredChatServerNodeEndPoint.toByteArray(message);
             for(Session node : ClusteredChatServerNodeEndPoint.nodes)
                 node.getBasicRemote().sendBinary(ByteBuffer.wrap(bytes));
         }
         catch(IOException e)
         {
             System.err.println("ERROR: Exception when notifying of left node");
             e.printStackTrace();
         }
    }

    @OnMessage
    public void onMessage(Session session, byte[] message)
    {
        try
        {
            for(Session node : ClusteredChatServerNodeEndPoint.nodes)
            {
                if(node != session)
                    node.getBasicRemote().sendBinary(ByteBuffer.wrap(message));
            }
        }
        catch(IOException e)
        {
            System.err.println("ERROR: Exception when handling message on server");
            e.printStackTrace();
        }
    }

    private static byte[] toByteArray(ClusterMessage message) throws IOException
    {
        try(ByteArrayOutputStream output = new ByteArrayOutputStream();
            ObjectOutputStream stream = new ObjectOutputStream(output))
        {
            stream.writeObject(message);
            return output.toByteArray();
        }
    }
}
```

This class utilizes the `@ServerEndpoint` annotation to define a WebSocket endpoint at the specified path ("/charityChat/{nodeId}"). The `onOpen`, `onClose`, and `onMessage` methods handle various WebSocket events.

## Servlet Implementation for Communication

Now, let's review the `ClusterNodeServlet` class, which acts as a WebSocket client to communicate with the WebSocket endpoints:

```java
@ClientEndpoint
public class ClusterNodeServlet extends HttpServlet {
     private Session session;
    private String nodeId;
    @Override
    public void init() throws ServletException
    {
        this.nodeId = this.getInitParameter("nodeId");
        String path = this.getServletContext().getContextPath() +
                "/charityChat/" + this.nodeId;
        try
        {
            URI uri = new URI("ws", "localhost:8080", path, null, null);
            this.session = ContainerProvider.getWebSocketContainer()
                    .connectToServer(this, uri);
        }
        catch(URISyntaxException | IOException | DeploymentException e)
        {
            throw new ServletException("Cannot connect to " + path + ".", e);
        }
    }
    @Override
    public void destroy()
    {
        try
        {
            this.session.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        ClusterMessage message = new ClusterMessage(this.nodeId,
                "request:{ip:\"" + request.getRemoteAddr() +
                "\",queryString:\"" + request.getQueryString() + "\"}");

        try(OutputStream output = this.session.getBasicRemote().getSendStream();
            ObjectOutputStream stream = new ObjectOutputStream(output))
        {
            stream.writeObject(message);
        }
        response.getWriter().append("OK");
    }
    @OnMessage
    public void onMessage(InputStream input)
    {
        try(ObjectInputStream stream = new ObjectInputStream(input))
        {
            ClusterMessage message = (ClusterMessage)stream.readObject();
            System.out.println("INFO (Node " + this.nodeId +
                    "): Message received from cluster; node = " +
                    message.getNodeId() + ", message = " + message.getMessage());
        }
        catch(IOException | ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }
private static final List<Session> nodes = new ArrayList<>(2);

    @OnOpen
    public void onOpen(Session session, @PathParam("nodeId") String nodeId) {
        System.out.println("INFO: Node [" + nodeId + "] connected to cluster.");
        ClusterMessage message = new ClusterMessage(nodeId, "Joined the cluster.");
        try
        {
            byte[] bytes = ClusteredChatServerNodeEndPoint.toByteArray(message);
            for(Session node : ClusteredChatServerNodeEndPoint.nodes)
                node.getBasicRemote().sendBinary(ByteBuffer.wrap(bytes));
        }
        catch(IOException e)
        {
            System.err.println("ERROR: Exception when notifying of new node");
            e.printStackTrace();
        }
        ClusteredChatServerNodeEndPoint.nodes.add(session);

    }

    @OnClose
    public void onClose(Session session, @PathParam("nodeId") String nodeId)
    {
    	 System.out.println("INFO: Node [" + nodeId + "] disconnected.");

    	 ClusteredChatServerNodeEndPoint.nodes.remove(session);

         ClusterMessage message = new ClusterMessage(nodeId, "Left the cluster.");
         try
         {
             byte[] bytes = ClusteredChatServerNodeEndPoint.toByteArray(message);
             for(Session node : ClusteredChatServerNodeEndPoint.nodes)
                 node.getBasicRemote().sendBinary(ByteBuffer.wrap(bytes));
         }
         catch(IOException e)
         {
             System.err.println("ERROR: Exception when notifying of left node");
             e.printStackTrace();
         }
    }

    @OnMessage
    public void onMessage(Session session, byte[] message)
    {
        try
        {
            for(Session node : ClusteredChatServerNodeEndPoint.nodes)
            {
                if(node != session)
                    node.getBasicRemote().sendBinary(ByteBuffer.wrap(message));
            }
        }
        catch(IOException e)
        {
            System.err.println("ERROR: Exception when handling message on server");
            e.printStackTrace();
        }
    }

    private static byte[] toByteArray(ClusterMessage message) throws IOException
    {
        try(ByteArrayOutputStream output = new ByteArrayOutputStream();
            ObjectOutputStream stream = new ObjectOutputStream(output))
        {
            stream.writeObject(message);
            return output.toByteArray();
        }
    }    @OnClose
    public void onClose(CloseReason reason)
    {
        CloseReason.CloseCode code = reason.getCloseCode();
        if(code != CloseReason.CloseCodes.NORMAL_CLOSURE)
        {
            System.err.println("ERROR: WebSocket connection closed unexpectedly;" +
                    " code = " + code + ", reason = " + reason.getReasonPhrase());
        }
    }
}
```

This class is annotated with `@ClientEndpoint` to designate it as a WebSocket client. It initiates a WebSocket connection in the `init` method and handles incoming messages and connection closure events.

## Message Object

The `ClusterMessage` class represents the messages exchanged between nodes:

```java
public class ClusterMessage implements Serializable {
     private String nodeId;
    private String message;

    public ClusterMessage()
    {
    }
    public ClusterMessage(String nodeId, String message)
    {
        this.nodeId = nodeId;
        this.message = message;
    }
    public String getNodeId()
    {
        return nodeId;
    }
    public void setNodeId(String nodeId)
    {
        this.nodeId = nodeId;
    }
    public String getMessage()
    {
        return message;
    }
    public void setMessage(String message)
    {
        this.message = message;
    }
}
```

This simple Serializable class encapsulates the nodeId and message content for communication.

## Conclusion

By integrating WebSockets into your clustered environment, you enable real-time communication between nodes. This example demonstrates how to configure servlets, implement WebSocket endpoints, and establish communication channels within a cluster. Feel free to adapt this code to suit your specific requirements and further enhance your clustered application.