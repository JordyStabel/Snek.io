package com.s3.SnekIO.websocketserver.endpoint;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.s3.snekio.websocketshared.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.ArrayList;
import java.util.List;

@ServerEndpoint(value = "/test/")
public class TestEndpoint {

    private static final Logger logger = LoggerFactory.getLogger(TestEndpoint.class);
    private static final List<Session> sessions = new ArrayList<>();

    @OnOpen
    public void onOpen(Session session) {
        logger.info("Connected SessionID: {}", session.getId());

        sessions.add(session);
        logger.info("Session added. Session count: {}", sessions.size());
    }

    @OnMessage
    public void onText(String message, Session session) {
        logger.info("Session ID: {} Received: {}", session.getId(), message);
        handleMessageFromClient(message, session);
    }

    @OnClose
    public void onClose(CloseReason reason, Session session) {
        logger.info("Session ID: {} Reason: {}", session, reason);
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
}
