package com.s3.SnekIO.websocketserver.messagelogic;

import com.s3.SnekIO.websocketserver.endpoints.GameEndpoint;
import com.s3.SnekIO.websocketserver.messagegenerator.GameMessageGenerator;
import com.s3.SnekIO.websocketserver.util.MessageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameMessageLogic implements IGameMessageLogic {

    private static final Logger logger = LoggerFactory.getLogger(GameMessageLogic.class);

    GameEndpoint gameEndpoint;
    GameMessageGenerator gameMessageGenerator;

    @Override
    public void setEndPoint(GameEndpoint gameEndpoint) {
        this.gameEndpoint = gameEndpoint;
        gameMessageGenerator = new GameMessageGenerator(gameEndpoint);
    }

    @Override
    public void sendGlobalMessage(String msg) {
        logger.info("Message: {}", msg);
        gameMessageGenerator.GlobalMessageConstructor("UpdateBall", MessageHelper.getGlobalMessageString(msg));
        logger.info("Message: {}", MessageHelper.getGlobalMessageString(msg));
    }
}
