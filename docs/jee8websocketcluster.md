# 3. Using WebSockets to communicate in a Cluster.

To demonstrate two servlets deployed on two cluster nodes communicating with each other via WebSockets, you'll need to set up Apache Tomcat clustering. In this example, we'll create a simple chat application where one servlet broadcasts messages to all connected clients, including clients connected to different cluster nodes. Here are the two servlets:

Servlet 1 (Node 1):
```java
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/chat")
public class ClusteredChatServerNode1 {
    private static Set<Session> sessions = new HashSet<>();

    @OnOpen
    public void onOpen(Session session) {
        sessions.add(session);
    }

    @OnClose
    public void onClose(Session session) {
        sessions.remove(session);
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        // Broadcast the message to all connected clients, including Node 2.
        broadcast("Node 1: " + message);
    }

    private void broadcast(String message) {
        for (Session session : sessions) {
            try {
                session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
```

Servlet 2 (Node 2):
```java
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/chat")
public class ClusteredChatServerNode2 {
    private static Set<Session> sessions = new HashSet<>();

    @OnOpen
    public void onOpen(Session session) {
        sessions.add(session);
    }

    @OnClose
    public void onClose(Session session) {
        sessions.remove(session);
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        // Broadcast the message to all connected clients, including Node 1.
        broadcast("Node 2: " + message);
    }

    private void broadcast(String message) {
        for (Session session : sessions) {
            try {
                session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
```

These two servlets have the same WebSocket endpoint ("/chat") and use the `broadcast` method to send messages to all connected clients. They are deployed on different cluster nodes (Node 1 and Node 2). When a message is sent from one node, it will be broadcasted to all connected clients, including clients connected to the other node, thanks to Tomcat clustering.

To set up Tomcat clustering, please refer to the official Apache Tomcat clustering documentation for detailed instructions on how to configure clustering in your environment. This will enable the WebSocket communication between the two servlets deployed on different nodes.