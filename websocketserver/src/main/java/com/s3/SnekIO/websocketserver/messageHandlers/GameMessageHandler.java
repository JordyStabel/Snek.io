package com.s3.SnekIO.websocketserver.messageHandlers;

import com.google.gson.Gson;
import com.s3.SnekIO.websocketserver.game.Game;
import com.s3.SnekIO.websocketserver.game.PlayerLogic;
import com.s3.SnekIO.websocketshared.models.InputMouse;
import com.s3.SnekIO.websocketshared.models.Player;
import com.s3.SnekIO.websocketshared.actions.Register;
import com.s3.SnekIO.websocketshared.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameMessageHandler implements IMessageHandler {

    private Game game;
    private PlayerLogic playerLogic;
    private static Gson gson = new Gson();
    private static final Logger logger = LoggerFactory.getLogger(GameMessageHandler.class);

    public GameMessageHandler(Game game) {
        this.game = game;
        playerLogic = new PlayerLogic();
    }

    @Override
    public void handelMessage(String json, String sessionId) {
        Message message = gson.fromJson(json, Message.class);

        switch (message.getAction()) {
            case REGISTER:
                Register register = (Register) message.parseData(Register.class);
                registerPlayer(register, sessionId);
                break;
            case INPUTMOUSE:
                InputMouse inputMouse = (InputMouse) message.parseData(InputMouse.class);
                Player player = game.findPlayer(sessionId);

                if (player == null) {
                    logger.error("PlayerLogic can't be null");
                    return;
                }
                playerLogic.updatePlayerInputMouse(player, inputMouse);
                break;
            default:
                logger.error("No valid action");
        }
    }

    private void registerPlayer(Register register, String sessionId) {
        if (game.findPlayer(sessionId) == null) {
            Player player = new Player(register.getName(), sessionId, register.getUuid(), register.getColor(), register.getStartingPosition());
            game.addPlayer(player);
        }
    }

    @Override
    public void disconnected(String sessionId) {
        game.removePlayer(sessionId);
    }
}
