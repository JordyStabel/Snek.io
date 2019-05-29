package com.s3.SnekIO.websocketserver.endpoint;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.s3.SnekIO.websocketshared.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@ServerEndpoint(value = "/test/")
public class TestEndpoint {

    private static final Logger logger = LoggerFactory.getLogger(TestEndpoint.class);
    private static final List<Session> sessions = new ArrayList<>();

    @OnOpen
    public void onOpen(Session session) throws IOException, InterruptedException {
        logger.info("Connected SessionID: {}", session.getId());

        sessions.add(session);
        logger.info("Session added. Session count: {}", sessions.size());

        Random random = new Random();
        int x = 500;
        int y = 500;
        int r = 35;

        for (int i = 0; i < 50; i++) {
            x += random.nextInt(50) - 20;
            y += random.nextInt(50) - 20;
            r += random.nextInt(11) - 5;
            if (r <= 10){
                r = 10;
            }
            sendGlobalMessage(x, y, r);
            Thread.sleep(2500);
        }
    }

    @OnMessage
    public void onText(String message, Session session) {
        logger.info("Session ID: {} Received: {}", session.getId(), message);
        handleMessageFromClient(message, session);
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

    public void sendGlobalMessage(int x, int y, int r) {
        String info = x + "," + y + "," + r;

        for(javax.websocket.Session session:sessions){
            try {
                session.getBasicRemote().sendText(info);
            } catch (IOException e){
                logger.error("Error @ TestEndpoint.sendGlobalMessage: {0}", e);
            }
        }
    }
}
