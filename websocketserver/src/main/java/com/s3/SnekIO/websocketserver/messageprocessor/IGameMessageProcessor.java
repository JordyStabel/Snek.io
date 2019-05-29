package com.s3.SnekIO.websocketserver.messageprocessor;

public interface IGameMessageProcessor {
    void processMessage(String msg, String sessionId);
}
