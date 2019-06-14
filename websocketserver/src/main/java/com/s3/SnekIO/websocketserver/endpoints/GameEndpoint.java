package com.s3.SnekIO.websocketserver.endpoints;

import com.google.gson.Gson;
import com.s3.SnekIO.websocketserver.messageHandlers.IMessageHandler;
import com.s3.SnekIO.websocketshared.util.IJson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashSet;

@ServerEndpoint(value = "/test/")
public class GameEndpoint implements IEndPoint {

    private static final Logger logger = LoggerFactory.getLogger(GameEndpoint.class);
    private static HashSet<Session> sessions = new HashSet<>();

    private static IMessageHandler messageHandler;

    Gson gson = new Gson();

    public GameEndpoint() {}

    public static void setMessageHandler(IMessageHandler messageHandler) {
        GameEndpoint.messageHandler = messageHandler;
    }

    @OnOpen
    public void onOpen(Session session) {
        logger.info("Connected SessionID: {}", session.getId());
        sessions.add(session);
        logger.info("Session added. Session count: {}", sessions.size());
    }

    @OnMessage
    public void onText(String message, Session session) {
        logger.info("Session ID: {} Received: {}", session.getId(), message);
        //handleMessageFromClient(Message, session);
        //gameMessageProcessor.processMessage(message, session.getId());
        messageHandler.handelMessage(message, session.getId());
    }

    @OnClose
    public void onClose(CloseReason reason, Session session) {
        logger.info("Session ID: {} Reason: {}", session.getId(), reason);
        messageHandler.disconnected(session.getId());
        sessions.remove(session);
    }

    @OnError
    public void onError(Throwable error, Session session) {
        logger.error("Session ID: {} Error: {}", session.getId(), error.getMessage());
    }

    public void sendGlobalMessage(String message) {
        logger.info("Message: {}", message);
        for (javax.websocket.Session session : sessions) {
            try {
                session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                logger.error("Error @ GameEndpoint.sendGlobalMessage: {0}", e);
            }
        }
    }

    private void broadcast(String message) {
        for (Session session : sessions) {
            session.getAsyncRemote().sendText(message);
        }
        logger.info("Broadcasted: {}", message);
    }

    @Override
    public void sendTo(IJson json, String sessionId) {
        for (Session session : sessions) {
            if (session.getId().equals(sessionId)) {
                session.getAsyncRemote().sendText(json.toJson());
            }
        }
    }

    @Override
    public void broadcast(IJson json) {
        broadcast(json.toJson());
    }
}
