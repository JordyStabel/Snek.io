package com.s3.SnekIO.websocketserver.messages;

public class SendingEncapsulatingMessage {

    private String messageType;
    private Object messageData;

    public SendingEncapsulatingMessage(String messageType, Object messageData) {
        this.messageType = messageType;
        this.messageData = messageData;
    }

    public String getMessageType() {
        return messageType;
    }

    public Object getMessageData() {
        return messageData;
    }
}
