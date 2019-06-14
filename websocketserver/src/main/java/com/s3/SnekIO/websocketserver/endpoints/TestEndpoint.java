package com.s3.SnekIO.websocketserver.endpoints;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.s3.SnekIO.websocketserver.messageHandlers.IMessageHandler;
import com.s3.SnekIO.websocketserver.messagelogic.IGameMessageLogic;
import com.s3.SnekIO.websocketserver.messageprocessor.IGameMessageProcessor;
import com.s3.SnekIO.websocketshared.util.IJson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashSet;

@ServerEndpoint(value = "/test/")
public class TestEndpoint implements IEndPoint {

    private static final Logger logger = LoggerFactory.getLogger(TestEndpoint.class);
    private static HashSet<Session> sessions = new HashSet<>();

    private static IMessageHandler messageHandler;

    IGameMessageLogic gameMessageLogic;
    IGameMessageProcessor gameMessageProcessor;
    Gson gson = new Gson();

    public TestEndpoint(IGameMessageLogic gameMessageLogic, IGameMessageProcessor gameMessageProcessor) {
        this.gameMessageLogic = gameMessageLogic;
        this.gameMessageProcessor = gameMessageProcessor;
    }

    public TestEndpoint() {}

    public static void setMessageHandler(IMessageHandler messageHandler) {
        TestEndpoint.messageHandler = messageHandler;
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
                logger.error("Error @ TestEndpoint.sendGlobalMessage: {0}", e);
            }
        }
    }

    private void broadcast(String message) {
        for (javax.websocket.Session session : sessions) {
            session.getAsyncRemote().sendText(message);
        }
        logger.info("Broadcasted: {}", message);
    }

    @Override
    public void sendTo(IJson json, String sessionId) {

    }

    @Override
    public void broadcast(IJson json) {
        broadcast(json.toJson());
    }
}
