package com.s3.SnekIO.websocketserver.messagegenerator;

import com.s3.SnekIO.websocketserver.WebsocketServer;
import com.s3.SnekIO.websocketserver.endpoints.IEndPoint;
import com.s3.SnekIO.websocketserver.game.Game;
import com.s3.SnekIO.websocketserver.endpoints.TestEndpoint;
import com.s3.SnekIO.websocketshared.actions.Actions;
import com.s3.SnekIO.websocketshared.message.Message;
import com.s3.SnekIO.websocketshared.models.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageGenerator implements IMessageGenerator {

    private IEndPoint endPoint;
    private Game game;

    private static final Logger logger = LoggerFactory.getLogger(WebsocketServer.class);

    public MessageGenerator(IEndPoint endPoint) {
        this.endPoint = endPoint;
    }

    @Override
    public void updatePlayers() {
        Player player = new Player("Henk");
        Message message = new Message(Actions.UPDATE, player);
        endPoint.broadcast(message);
        logger.info("Message broadcasted: {}", message);
    }

    @Override
    public void setGame(Game game) {
        this.game = game;
    }
}
