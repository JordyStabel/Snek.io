package com.s3.SnekIO.websocketserver.messageprocessor;

import com.google.gson.Gson;
import com.s3.SnekIO.websocketserver.messageHandlers.UpdateBallHandler;
import com.s3.SnekIO.websocketserver.messagelogic.IGameMessageLogic;
import com.s3.SnekIO.websocketserver.messages.ReveivingEncapsulatingMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameMessageProcessor implements IGameMessageProcessor {

    private static final Logger logger = LoggerFactory.getLogger(GameMessageProcessor.class);

    private IGameMessageLogic gameMessageLogic;

    public GameMessageProcessor(IGameMessageLogic gameMessageLogic) {
        this.gameMessageLogic = gameMessageLogic;
    }

    @Override
    public void processMessage(String message, String sessionId) {
        Gson gson = new Gson();

        logger.info("Message: {}", message);

        ReveivingEncapsulatingMessage messageObject = gson.fromJson(message, ReveivingEncapsulatingMessage.class);
        logger.info("Type: {} Data: {}", messageObject.getMessageType(), messageObject.getMessageData());

        switch (messageObject.getMessageType()) {
            case "UpdateBall":
                UpdateBallHandler updateBallHandler = new UpdateBallHandler(gameMessageLogic);
                logger.info("MessageData: {}", messageObject.getMessageData());
                updateBallHandler.sendUpdatedBallInfo(messageObject.getMessageData());
                break;
            default:
                logger.error("Unknown action");
                break;

        }
    }
}
