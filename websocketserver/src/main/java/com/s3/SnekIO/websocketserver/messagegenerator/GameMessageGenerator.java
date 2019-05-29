package com.s3.SnekIO.websocketserver.messagegenerator;

import com.google.gson.Gson;
import com.s3.SnekIO.websocketserver.endpoints.TestEndpoint;
import com.s3.SnekIO.websocketserver.messages.SendingEncapsulatingMessage;

public class GameMessageGenerator implements IGameMessageGenerator {

    TestEndpoint testEndpoint;
    Gson gson = new Gson();

    public GameMessageGenerator(TestEndpoint endpoint) {
        this.testEndpoint = endpoint;
    }

    @Override
    public void MessageConstructor(String action, String object, String targetSessionId) {
        // TODO: Implement this
    }

    @Override
    public void GlobalMessageConstructor(String action, Object object) {
        testEndpoint.sendGlobalMessage(gson.toJson(new SendingEncapsulatingMessage(action, object)));
    }
}
