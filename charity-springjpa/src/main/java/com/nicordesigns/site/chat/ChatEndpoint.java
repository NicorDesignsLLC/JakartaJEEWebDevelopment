package com.nicordesigns.site.chat;

import com.nicordesigns.site.SessionRegistry;
import com.nicordesigns.site.UserAdminPrincipal;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.web.socket.server.standard.SpringConfigurator;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import javax.websocket.CloseReason;
import javax.websocket.EncodeException;
import javax.websocket.HandshakeResponse;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.PongMessage;
import javax.websocket.Session;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import javax.websocket.server.ServerEndpointConfig;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ScheduledFuture;
import java.util.function.Consumer;

@ServerEndpoint(value = "/charityRegistrationChat/{sessionId}",
        encoders = ChatMessageCodec.class,
        decoders = ChatMessageCodec.class,
        configurator = ChatEndpoint.EndpointConfigurator.class)
public class ChatEndpoint
{
    private static final Logger log = LogManager.getLogger();
    private static final byte[] pongData =
            "This is PONG country.".getBytes(StandardCharsets.UTF_8);

    private final Consumer<HttpSession> callback = this::httpSessionRemoved;

    private boolean closed = false;
    private Session wsSession;
    private Session otherWsSession;
    private HttpSession httpSession;
    private ChatSession chatSession;
    private Principal principal;
    private ScheduledFuture<?> pingFuture;
    private Locale locale;
    private Locale otherLocale;

    @Inject SessionRegistry sessionRegistry;
    @Inject ChatService chatService;
    @Inject MessageSource messageSource;
    @Inject TaskScheduler taskScheduler;

    @OnOpen
    public void onOpen(Session session, @PathParam("sessionId") long sessionId)
    {
        log.entry(sessionId);
        this.httpSession = EndpointConfigurator.getExposedSession(session);
        this.principal = EndpointConfigurator.getExposedPrincipal(session);
        this.locale = EndpointConfigurator.getExposedLocale(session);

        try
        {
            if(this.principal == null)
            {
                log.warn("Unauthorized attempt to access chat server.");
                session.close(new CloseReason(
                        CloseReason.CloseCodes.VIOLATED_POLICY,
                        this.messageSource.getMessage(
                                "error.chat.not.logged.in", null, this.locale)
                ));
                return;
            }

            if(sessionId < 1)
            {
                CreateResult result =
                        this.chatService.createSession(this.principal.getName());
                this.chatSession = result.getChatSession();
                this.chatSession.setCustomer(session);
                this.chatSession.setOnRepresentativeJoin(
                        s -> this.otherWsSession = s
                );
                session.getBasicRemote().sendObject(this.cloneAndLocalize(
                        result.getCreateMessage(), this.locale
                ));
            }
            else
            {
                JoinResult result = this.chatService.joinSession(sessionId,
                        this.principal.getName());
                if(result == null)
                {
                    log.warn("Attempted to join non-existent chat session {}.",
                            sessionId);
                    session.close(new CloseReason(
                            CloseReason.CloseCodes.UNEXPECTED_CONDITION,
                            this.messageSource.getMessage(
                                    "error.chat.no.session", null, this.locale)
                    ));
                    return;
                }
                this.chatSession = result.getChatSession();
                this.chatSession.setRepresentative(session);
                this.otherWsSession = this.chatSession.getCustomer();
                this.otherLocale = EndpointConfigurator
                        .getExposedLocale(this.otherWsSession);

                session.getBasicRemote().sendObject(this.cloneAndLocalize(
                        this.chatSession.getCreationMessage(), this.locale
                ));
                session.getBasicRemote().sendObject(this.cloneAndLocalize(
                        result.getJoinMessage(), this.locale
                ));
                this.otherWsSession.getBasicRemote()
                        .sendObject(this.cloneAndLocalize(
                                result.getJoinMessage(), this.otherLocale
                        ));
            }

            this.wsSession = session;
            log.debug("onMessage completed successfully for chat {}.", sessionId);
        }
        catch(IOException | EncodeException e)
        {
            this.onError(e);
        }
        finally
        {
            log.exit();
        }
    }

    @OnMessage
    public void onMessage(ChatMessage message)
    {
        if(this.closed)
        {
            log.warn("Chat message received after connection closed.");
            return;
        }

        log.entry();
        message.setUser(this.principal.getName());
        this.chatService.logMessage(this.chatSession, message);
        try
        {
            this.wsSession.getBasicRemote().sendObject(message);
            this.otherWsSession.getBasicRemote().sendObject(message);
        }
        catch(IOException | EncodeException e)
        {
            this.onError(e);
        }
        log.exit();
    }

    @OnClose
    public void onClose(CloseReason reason)
    {
        if(reason.getCloseCode() != CloseReason.CloseCodes.NORMAL_CLOSURE)
        {
            log.warn("Abnormal closure {} for reason [{}].",
                    reason.getCloseCode(), reason.getReasonPhrase());
        }

        synchronized(this)
        {
            if(this.closed)
                return;
            this.close(ChatService.ReasonForLeaving.NORMAL, null);
        }
    }

    @OnError
    public void onError(Throwable e)
    {
        log.warn("Error received in WebSocket session.", e);

        synchronized(this)
        {
            if(this.closed)
                return;
            this.close(ChatService.ReasonForLeaving.ERROR,
                    "error.chat.closed.exception");
        }
    }

    private void sendPing()
    {
        if(!this.wsSession.isOpen())
            return;
        log.debug("Sending ping to WebSocket client.");
        try
        {
            this.wsSession.getBasicRemote()
                    .sendPing(ByteBuffer.wrap(ChatEndpoint.pongData));
        }
        catch(IOException e)
        {
            log.warn("Failed to send ping message to WebSocket client.", e);
        }
    }

    @OnMessage
    public void onPong(PongMessage message)
    {
        ByteBuffer data = message.getApplicationData();
        if(!Arrays.equals(ChatEndpoint.pongData, data.array()))
            log.warn("Received pong message with incorrect payload.");
        else
            log.debug("Received good pong message.");
    }

    @PostConstruct
    public void initialize()
    {
        this.sessionRegistry.registerOnRemoveCallback(this.callback);

        this.pingFuture = this.taskScheduler.scheduleWithFixedDelay(
                this::sendPing,
                new Date(System.currentTimeMillis() + 25_000L),
                25_000L
        );
    }

    private void httpSessionRemoved(HttpSession httpSession)
    {
        if(httpSession == this.httpSession)
        {
            synchronized(this)
            {
                if(this.closed)
                    return;
                log.info("Chat session ended abruptly by {} logging out.",
                        this.principal.getName());
                this.close(ChatService.ReasonForLeaving.LOGGED_OUT, null);
            }
        }
    }

    private void close(ChatService.ReasonForLeaving reason, String unexpected)
    {
        this.closed = true;
        if(!this.pingFuture.isCancelled())
            this.pingFuture.cancel(true);
        this.sessionRegistry.deregisterOnRemoveCallback(this.callback);
        ChatMessage message = null;
        if(this.chatSession != null)
            message = this.chatService.leaveSession(this.chatSession,
                this.principal.getName(), reason);

        if(message != null)
        {
            CloseReason.CloseCode closeCode;
            String reasonCode;
            if(reason == ChatService.ReasonForLeaving.ERROR)
            {
                closeCode = CloseReason.CloseCodes.UNEXPECTED_CONDITION;
                reasonCode = unexpected;
            }
            else
            {
                closeCode = CloseReason.CloseCodes.NORMAL_CLOSURE;
                reasonCode = "message.chat.ended";
            }

            //noinspection SynchronizeOnNonFinalField
            synchronized(this.wsSession)
            {
                if(this.wsSession.isOpen())
                {
                    try
                    {
                        this.wsSession.getBasicRemote()
                                .sendObject(this.cloneAndLocalize(
                                        message, this.locale
                                ));
                        this.wsSession.close(new CloseReason(
                                closeCode, this.messageSource.getMessage(
                                reasonCode, null, this.locale)
                        ));
                    }
                    catch(IOException | EncodeException e)
                    {
                        log.error("Error closing chat connection.", e);
                    }
                }
            }

            if(this.otherWsSession != null)
            {
                //noinspection SynchronizeOnNonFinalField
                synchronized(this.otherWsSession)
                {
                    if(this.otherWsSession.isOpen())
                    {
                        try
                        {
                            this.otherWsSession.getBasicRemote()
                                    .sendObject(this.cloneAndLocalize(
                                            message, this.otherLocale
                                    ));
                            this.otherWsSession.close(new CloseReason(
                                    closeCode, this.messageSource.getMessage(
                                    reasonCode, null, this.otherLocale)
                            ));
                        }
                        catch(IOException | EncodeException e)
                        {
                            log.error("Error closing companion chat connection.", e);
                        }
                    }
                }
            }
        }
    }

    private ChatMessage cloneAndLocalize(ChatMessage message, Locale locale)
    {
        message = message.clone();
        message.setLocalizedContent(this.messageSource.getMessage(
                message.getContentCode(), message.getContentArguments(), locale
        ));
        return message;
    }

    public static class EndpointConfigurator extends SpringConfigurator
    {
        private static final String HTTP_SESSION_KEY = "com.nicordesigns.ws.http.session";
        private static final String PRINCIPAL_KEY = "com.nicordesigns.ws.user.principal";
        private static final String LOCALE_KEY = "com.nicordesigns.ws.user.locale";

        @Override
        public void modifyHandshake(ServerEndpointConfig config,
                                    HandshakeRequest request,
                                    HandshakeResponse response)
        {
            log.entry();
            super.modifyHandshake(config, request, response);

            HttpSession httpSession = (HttpSession)request.getHttpSession();
            config.getUserProperties().put(HTTP_SESSION_KEY, httpSession);
            config.getUserProperties()
                    .put(PRINCIPAL_KEY, UserAdminPrincipal.getPrincipal(httpSession));
            config.getUserProperties().put(LOCALE_KEY,
                    LocaleContextHolder.getLocale());

            log.exit();
        }

        private static HttpSession getExposedSession(Session session)
        {
            return (HttpSession)session.getUserProperties().get(HTTP_SESSION_KEY);
        }

        private static Principal getExposedPrincipal(Session session)
        {
            return (Principal)session.getUserProperties().get(PRINCIPAL_KEY);
        }

        private static Locale getExposedLocale(Session session)
        {
            return (Locale)session.getUserProperties().get(LOCALE_KEY);
        }
    }
}
