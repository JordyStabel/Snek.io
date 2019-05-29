package com.s3.SnekIO.websocketserver.messages;

public class ReveivingEncapsulatingMessage {

    private String messageType;
    private String messageData;

    public ReveivingEncapsulatingMessage(String messageType, String messageData) {
        this.messageType = messageType;
        this.messageData = messageData;
    }

    public String getMessageType() {
        return messageType;
    }

    public String getMessageData() {
        return messageData;
    }
}
