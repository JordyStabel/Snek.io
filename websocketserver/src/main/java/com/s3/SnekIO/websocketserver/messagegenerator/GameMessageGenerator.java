package com.s3.SnekIO.websocketserver.messagegenerator;

import com.google.gson.Gson;
import com.s3.SnekIO.websocketserver.endpoints.GameEndpoint;
import com.s3.SnekIO.websocketserver.messages.SendingEncapsulatingMessage;

public class GameMessageGenerator implements IGameMessageGenerator {

    GameEndpoint gameEndpoint;
    Gson gson = new Gson();

    public GameMessageGenerator(GameEndpoint endpoint) {
        this.gameEndpoint = endpoint;
    }

    @Override
    public void MessageConstructor(String action, String object, String targetSessionId) {
        // TODO: Implement this
    }

    @Override
    public void GlobalMessageConstructor(String action, Object object) {
        gameEndpoint.sendGlobalMessage(gson.toJson(new SendingEncapsulatingMessage(action, object)));
    }
}
