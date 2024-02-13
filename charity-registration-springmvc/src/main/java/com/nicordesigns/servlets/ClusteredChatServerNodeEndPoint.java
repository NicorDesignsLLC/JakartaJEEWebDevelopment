package com.nicordesigns.servlets;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

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