package com.s3.SnekIO.websocketserver.messagelogic;

import com.s3.SnekIO.websocketserver.endpoints.TestEndpoint;
import com.s3.SnekIO.websocketserver.messagegenerator.GameMessageGenerator;
import com.s3.SnekIO.websocketserver.util.MessageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameMessageLogic implements IGameMessageLogic {

    private static final Logger logger = LoggerFactory.getLogger(GameMessageLogic.class);

    TestEndpoint testEndpoint;
    GameMessageGenerator gameMessageGenerator;

    @Override
    public void setEndPoint(TestEndpoint testEndpoint) {
        this.testEndpoint = testEndpoint;
        gameMessageGenerator = new GameMessageGenerator(testEndpoint);
    }

    @Override
    public void sendGlobalMessage(String msg) {
        logger.info("Message: {}", msg);
        gameMessageGenerator.GlobalMessageConstructor("UpdateBall", MessageHelper.getGlobalMessageString(msg));
        logger.info("Message: {}", MessageHelper.getGlobalMessageString(msg));
    }
}
