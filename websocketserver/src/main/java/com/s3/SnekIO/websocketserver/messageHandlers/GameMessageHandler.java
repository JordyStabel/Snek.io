package com.s3.SnekIO.websocketserver.messageHandlers;

import com.google.gson.Gson;
import com.s3.SnekIO.websocketserver.messageprocessor.GameMessageProcessor;
import com.s3.SnekIO.websocketshared.actions.Actions;
import com.s3.SnekIO.websocketshared.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.s3.SnekIO.websocketshared.actions.Actions.*;

public class GameMessageHandler implements IMessageHandler {

    private static Gson gson = new Gson();
    private static final Logger logger = LoggerFactory.getLogger(GameMessageProcessor.class);

    public GameMessageHandler() {

    }

    @Override
    public void handelMessage(String json, String sessionId) {
        Message message = gson.fromJson(json, Message.class);

        switch (message.getAction()) {
            case UP:
                updatePlayer(Actions.UP);
                break;
            case DOWN:
                updatePlayer(Actions.DOWN);
                break;
            case LEFT:
                updatePlayer(LEFT);
                break;
            case RIGHT:
                updatePlayer(RIGHT);
                break;
            default:
                logger.error("No valid action");
        }
    }

    @Override
    public void disconnected(String sessionId) {

    }

    private void updatePlayer(Actions action) {
        // TODO: Make player specific

        switch (action) {
            case LEFT:

                break;
            case DOWN:

                break;
            case RIGHT:

                break;
            case UP:

                break;
            default:
                throw new IllegalArgumentException("No direction found");
        }
    }
}
