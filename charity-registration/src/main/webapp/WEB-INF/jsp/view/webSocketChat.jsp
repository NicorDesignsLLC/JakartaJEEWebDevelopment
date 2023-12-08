<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Clustered Chat</title>
    <script>
        let webSocket;

        function connect() {
            const nodeId = prompt("Enter your node ID:");
            webSocket = new WebSocket("ws://your-websocket-server-address/charityRegistrationChat/" + nodeId);

            webSocket.onopen = function (event) {
                console.log("WebSocket connection opened.");
            };

            webSocket.onmessage = function (event) {
                const messageOutput = document.getElementById("messageOutput");
                messageOutput.innerHTML += "<p>" + event.data + "</p>";
            };

            webSocket.onclose = function (event) {
                console.log("WebSocket connection closed.");
            };
        }

        function sendMessage() {
            const messageInput = document.getElementById("messageInput");
            const message = messageInput.value;
            webSocket.send(message);
            messageInput.value = "";
        }
    </script>
</head>
<body>
    <h2>Clustered Chat</h2>

    <button onclick="connect()">Connect to Cluster</button>

    <div>
        <div id="messageOutput"></div>
        <input type="text" id="messageInput" placeholder="Type a message...">
        <button onclick="sendMessage()">Send</button>
    </div>
</body>
</html>
