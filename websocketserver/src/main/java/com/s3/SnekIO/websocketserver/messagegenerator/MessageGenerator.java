package com.s3.SnekIO.websocketserver.messagegenerator;

import com.s3.SnekIO.websocketserver.WebsocketServer;
import com.s3.SnekIO.websocketserver.endpoints.IEndPoint;
import com.s3.SnekIO.websocketserver.game.Game;
import com.s3.SnekIO.websocketshared.actions.Actions;
import com.s3.SnekIO.websocketshared.message.Message;
import com.s3.SnekIO.websocketshared.models.GameState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class MessageGenerator implements IMessageGenerator {

    private IEndPoint endPoint;
    private Game game;

    private static final Logger logger = LoggerFactory.getLogger(WebsocketServer.class);

    public MessageGenerator(IEndPoint endPoint) {
        this.endPoint = endPoint;
    }

    @Override
    public void updatePlayers() {
//        if (game.getInputMouse() != null) {
//            Message message = new Message(Actions.INPUTMOUSE, game.getInputMouse());
//            endPoint.broadcast(message);
//            logger.info("Message broadcasted: {}", message);
//            return;
//        }

        try {
            GameState gameState = new GameState(game.getOrbs(), game.getPlayers());
            Message msg = new Message(Actions.GAMESTATE, gameState);
            endPoint.broadcast(msg);
            logger.info("Message broadcasted: {}", msg);
        }
        catch (Exception exception) {
            logger.error("Something went wrong: {0}", exception);
        }
    }

    @Override
    public void setGame(Game game) {
        this.game = game;
    }
}
