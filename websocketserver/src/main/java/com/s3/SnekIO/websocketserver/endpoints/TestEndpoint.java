package com.s3.SnekIO.websocketserver.endpoints;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.s3.SnekIO.websocketserver.messagelogic.IGameMessageLogic;
import com.s3.SnekIO.websocketserver.messageprocessor.IGameMessageProcessor;
import com.s3.SnekIO.websocketshared.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@ServerEndpoint(value = "/test/")
public class TestEndpoint {

    private static final Logger logger = LoggerFactory.getLogger(TestEndpoint.class);
    private static HashSet<Session> sessions = new HashSet<>();

    IGameMessageLogic gameMessageLogic;
    IGameMessageProcessor gameMessageProcessor;
    Gson gson = new Gson();

    public TestEndpoint(IGameMessageLogic gameMessageLogic, IGameMessageProcessor gameMessageProcessor) {
        this.gameMessageLogic = gameMessageLogic;
        this.gameMessageProcessor = gameMessageProcessor;
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
        //handleMessageFromClient(message, session);
        gameMessageProcessor.processMessage(message, session.getId());
    }

    @OnClose
    public void onClose(CloseReason reason, Session session) {
        logger.info("Session ID: {} Reason: {}", session.getId(), reason);
        sessions.remove(session);
    }

    @OnError
    public void onError(Throwable error, Session session) {
        logger.error("Session ID: {} Error: {}", session.getId(), error.getMessage());
    }

    private void handleMessageFromClient(String jsonMessage, Session session) {
        Gson gson = new Gson();

        logger.info("SessionID: {} json: {}", session.getId(), jsonMessage);

        try {
            Test message = gson.fromJson(jsonMessage, Test.class);
            logger.info("Session ID: {} Message handled: {}", session.getId(), message);
        } catch (JsonSyntaxException ex) {
            logger.error("Can't process message: {0}", ex);
        }
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
}
