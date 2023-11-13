# 1. Evolution from Ajax to WebSockets

## Introduction

In the world of web development, understanding the evolution from Ajax to WebSockets is crucial. This document provides insights into this transition and valuable resources to aid your learning journey.

### Relevant Documentation

1. Jakarta WebSocket Documentation:


   - [Jakarta EE WebSocket Specification](https://jakarta.ee/specifications/websocket/)
   - [Jakarta EE 8 WebSocket Documentation](https://jakarta.ee/specifications/websocket/1.1/)
   
### Maven Coordinates

For your project, you can use the following Maven coordinates to access the necessary WebSocket libraries:

- [jakarta.websocket-api 1.1.2](https://search.maven.org/artifact/jakarta.websocket/jakarta.websocket-api/1.1.2/jar)
- [jakarta.websocket-client-api 1.1.2](https://search.maven.org/artifact/jakarta.websocket/jakarta.websocket-client-api/1.1.2/jar)

## Ajax - Bridging the Gap

Ajax, which stands for Asynchronous JavaScript and XML, allows asynchronous communication with a server using JavaScript. Despite XML being part of the name, JSON is more commonly used for data exchange. Ajax enables parts of a web page to communicate with the backend server and update without requiring a full page refresh. An early example of Ajax's success was Google Maps, which outperformed MapQuest by incorporating Ajax technology.

However, Ajax faces a challenge with the stateless nature of HTTP. When backend data changes or updates, the web page remains unaware and doesn't fetch fresh data. The same issue occurs in the reverse direction: if the data changes in the browser, the server is unaware until data is POSTed back.

To tackle this problem, several approaches can be considered:

1. **Polling:** Periodically checking the backend server for updates, which can consume substantial bandwidth.

2. **Synchronization:** Updating backend data whenever the browser sends data to the server. This approach may lead to data inconsistencies and large data updates. It's not entirely compatible with the HTTP asynchronous protocol and may cause intermittent issues.

3. **HTTP 1.1 Transfer-Encoding:** Using chunked headers in combination with JavaScript XMLHttpRequest for more efficient communication. This approach is programming-intensive and error-prone.

## WebSockets to the Rescue

### Historical Perspective

WebSockets emerged as a solution to the limitations of traditional HTTP communication. The HTTP/1.1 protocol introduced the "HTTP/1.1 Upgrade" feature, which allowed clients to switch to a different protocol beyond HTTP. Servers responded with a "101 Switching protocols" status code, indicating the upgrade. This capability opened doors to transitioning to more stateful network protocols.

WebSockets introduced the URI schemes "ws" (WebSocket) and "wss" (Secure WebSocket), which operate on standard HTTP ports (80 and 443) and utilize the "Upgrade: WebSocket" header.

For detailed historical context, you can refer to [RFC 6455](https://www.rfc-editor.org/rfc/rfc6455).

### WebSockets Advantages

WebSockets offer numerous advantages over traditional Ajax-based communication:

1. **Port Compatibility:** WebSockets operate on standard HTTP ports (80 and 443), eliminating firewall issues.

2. **Native Browser Support:** Modern web browsers already include WebSocket support.

3. **Heartbeat Mechanism:** WebSockets maintain the connection with heartbeat acknowledgments, ensuring reliability.

4. **Flexible Message Handling:** No strict message encapsulation rules, allowing versatile data exchange.

5. **Graceful Connection Closure:** Provides a close connection message with reasons, enhancing error handling.

6. **Cross-Domain Capability:** WebSockets can facilitate cross-domain connections, as they are not based on HTTP.

7. **Support for New Protocols:** Enabling the use of newer protocols like [STOMP](https://stomp.github.io/) for applications requiring high connectivity and data throughput (e.g., IoT, gaming, and data-intensive applications like Smart Grids).


