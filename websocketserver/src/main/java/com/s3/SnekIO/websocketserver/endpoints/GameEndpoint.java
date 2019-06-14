package com.s3.SnekIO.websocketserver.endpoints;

import com.s3.SnekIO.websocketserver.messageHandlers.IMessageHandler;
import com.s3.SnekIO.websocketshared.util.IJson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.HashSet;

@ServerEndpoint(value = "/test/")
public class GameEndpoint implements IEndPoint {

    private static final Logger logger = LoggerFactory.getLogger(GameEndpoint.class);
    private static HashSet<Session> sessions = new HashSet<>();

    private static IMessageHandler messageHandler;

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

    @Override
    public void sendTo(IJson json, String sessionId) {
        for (Session session : sessions) {
            if (session.getId().equals(sessionId)) {
                session.getAsyncRemote().sendText(json.toJson());
                logger.info("Sent to: {}", sessionId);
            }
        }
    }

    @Override
    public void broadcast(IJson json) {
        String message = json.toJson();

        for (Session session : sessions) {
            session.getAsyncRemote().sendText(message);
        }
        logger.info("Broadcasted: {}", message);
    }
}
