package com.s3.SnekIO.websocketserver.messages;

public class GlobalGameMessage {
    private String message;

    public GlobalGameMessage(String msg) {
        this.message = msg;
    }

    public String getMessage() {
        return message;
    }
}
