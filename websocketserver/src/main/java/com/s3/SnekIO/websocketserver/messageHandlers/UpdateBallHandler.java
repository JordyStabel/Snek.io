package com.s3.SnekIO.websocketserver.messageHandlers;

import com.google.gson.Gson;
import com.s3.SnekIO.websocketserver.messagelogic.IGameMessageLogic;
import com.s3.SnekIO.websocketserver.messages.GlobalGameMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UpdateBallHandler {
    private static final Logger logger = LoggerFactory.getLogger(UpdateBallHandler.class);

    private IGameMessageLogic gameMessageLogic;

    public UpdateBallHandler(IGameMessageLogic gameMessageLogic) {
        this.gameMessageLogic = gameMessageLogic;
    }

    public void sendUpdatedBallInfo(String message) {
        Gson gson = new Gson();
        logger.info("Message: {}", message);
        GlobalGameMessage globalGameMessage = gson.fromJson(message, GlobalGameMessage.class);
        logger.info("Message: {}", globalGameMessage.getMessage());
        gameMessageLogic.sendGlobalMessage(globalGameMessage.getMessage());
    }
}
