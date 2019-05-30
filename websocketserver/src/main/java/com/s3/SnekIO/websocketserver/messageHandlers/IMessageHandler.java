package com.s3.SnekIO.websocketserver.messageHandlers;

public interface IMessageHandler {
    void handelMessage(String message, String sessionId);

    void disconnected(String sessionId);
}
