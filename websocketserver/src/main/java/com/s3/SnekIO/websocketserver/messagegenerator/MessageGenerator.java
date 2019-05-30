package com.s3.SnekIO.websocketserver.messagegenerator;

import com.s3.SnekIO.websocketserver.endpoints.TestEndpoint;
import com.s3.SnekIO.websocketshared.actions.Actions;
import com.s3.SnekIO.websocketshared.message.Message;

public class MessageGenerator implements IMessageGenerator {

    private TestEndpoint testEndpoint;

    public MessageGenerator(TestEndpoint endpoint) {
        this.testEndpoint = endpoint;
    }

    @Override
    public void updatePlayers() {
        testEndpoint.sendGlobalMessage("Henk");
    }
}
