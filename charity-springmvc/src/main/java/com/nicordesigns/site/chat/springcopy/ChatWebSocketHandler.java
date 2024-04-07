package com.nicordesigns.site.chat.springcopy;
import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class ChatWebSocketHandler extends TextWebSocketHandler {
	
	 @Autowired private ChatService chatService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // Handle connection establishment
    }

    //TODO Refactor the ChatContoller 
    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        Map<String, Object> model = session.getAttributes();

        String payload = message.getPayload();

        if (payload.equals("list")) {
            // Handle listing pending chat sessions
            model.put("sessions", chatService.getPendingSessions());
        } else if (payload.equals("new")) {
            // Handle creating a new chat session
            // Redirect or send appropriate WebSocket message
        	model.put("chatSessionId", 0);
        } else if (payload.startsWith("join")) {
            // Handle joining a chat session
            // Extract chat session ID from payload
            long chatSessionId = Long.parseLong(payload.substring("join".length()));
            model.put("chatSessionId", chatSessionId);
            // Redirect or send appropriate WebSocket message with chat session ID
        }

        // You can send appropriate WebSocket messages here based on the logic above
        
        //chatService.processChatMessage(payload);
        // Handle other WebSocket-related tasks as needed
    }
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // Handle connection closure
    }
}





