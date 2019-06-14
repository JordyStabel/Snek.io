package com.s3.SnekIO.websocketserver.messagegenerator;

import com.s3.SnekIO.websocketserver.endpoints.IEndPoint;
import com.s3.SnekIO.websocketserver.game.Game;
import com.s3.SnekIO.websocketshared.actions.Actions;
import com.s3.SnekIO.websocketshared.message.Message;
import com.s3.SnekIO.websocketshared.models.GameState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageGenerator implements IMessageGenerator {

    private IEndPoint endPoint;
    private Game game;

    private static final Logger logger = LoggerFactory.getLogger(MessageGenerator.class);

    public MessageGenerator(IEndPoint endPoint) {
        this.endPoint = endPoint;
    }

    @Override
    public void updatePlayers() {
        try {
            GameState gameState = new GameState(game.getOrbs(), game.getPlayers());
            Message message = new Message(Actions.GAMESTATE, gameState);
            endPoint.broadcast(message);
            logger.info("Message broadcasted: {}", message);
        } catch (Exception exception) {
            logger.error("ConcurrentModificationException");
        }
    }

    @Override
    public void sendToPlayer(String sessionId) {
        endPoint.sendTo(new Message(Actions.YOUDIED), sessionId);
    }

    @Override
    public void setGame(Game game) {
        this.game = game;
    }
}
