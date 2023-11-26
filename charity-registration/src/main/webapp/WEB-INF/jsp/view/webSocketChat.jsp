<%@ page import="java.util.*" %>
<%@ page import="javax.websocket.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>WebSocket Chat</title>
</head>
<body>

<h2>WebSocket Chat</h2>

<div id="chatOutput"></div>

<input type="text" id="messageInput" placeholder="Type a message...">
<button onclick="sendMessage()">Send</button>

<script>
    // Establish WebSocket connection
    const socket = new WebSocket("ws://your-websocket-server-address/chat");

    // Display incoming messages
    socket.onmessage = function (event) {
        const chatOutput = document.getElementById("chatOutput");
        chatOutput.innerHTML += "<p>" + event.data + "</p>";
    };

    // Send message to WebSocket server
    function sendMessage() {
        const messageInput = document.getElementById("messageInput");
        const message = messageInput.value;
        socket.send(message);
        messageInput.value = "";
    }
</script>

</body>
</html>
