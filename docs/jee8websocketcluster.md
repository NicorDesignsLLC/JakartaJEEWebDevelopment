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
@ServerEndpoint("/chat/{nodeId}")
public class ClusteredChatServerNodeEndPoint {
    // ... (existing code)

    @OnOpen
    public void onOpen(Session session, @PathParam("nodeId") String nodeId) {
        // ... (existing code)
    }

    @OnClose
    public void onClose(Session session, @PathParam("nodeId") String nodeId) {
        // ... (existing code)
    }

    @OnMessage
    public void onMessage(Session session, byte[] message) {
        // ... (existing code)
    }

    // ... (existing code)
}
```

This class utilizes the `@ServerEndpoint` annotation to define a WebSocket endpoint at the specified path ("/chat/{nodeId}"). The `onOpen`, `onClose`, and `onMessage` methods handle various WebSocket events.

## Servlet Implementation for Communication

Now, let's review the `ClusterNodeServlet` class, which acts as a WebSocket client to communicate with the WebSocket endpoints:

```java
@ClientEndpoint
public class ClusterNodeServlet extends HttpServlet {
    // ... (existing code)

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // ... (existing code)
    }

    @OnMessage
    public void onMessage(InputStream input) {
        // ... (existing code)
    }

    @OnClose
    public void onClose(CloseReason reason) {
        // ... (existing code)
    }

    // ... (existing code)
}
```

This class is annotated with `@ClientEndpoint` to designate it as a WebSocket client. It initiates a WebSocket connection in the `init` method and handles incoming messages and connection closure events.

## Message Object

The `ClusterMessage` class represents the messages exchanged between nodes:

```java
public class ClusterMessage implements Serializable {
    // ... (existing code)
}
```

This simple Serializable class encapsulates the nodeId and message content for communication.

## Conclusion

By integrating WebSockets into your clustered environment, you enable real-time communication between nodes. This example demonstrates how to configure servlets, implement WebSocket endpoints, and establish communication channels within a cluster. Feel free to adapt this code to suit your specific requirements and further enhance your clustered application.