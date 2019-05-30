package com.s3.SnekIO.websocketserver.messageHandlers;

import com.google.gson.Gson;
import com.s3.SnekIO.websocketserver.game.Game;
import com.s3.SnekIO.websocketshared.models.InputMouse;
import com.s3.SnekIO.websocketserver.game.Player;
import com.s3.SnekIO.websocketshared.actions.Actions;
import com.s3.SnekIO.websocketshared.actions.Register;
import com.s3.SnekIO.websocketshared.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.s3.SnekIO.websocketshared.actions.Actions.*;

public class GameMessageHandler implements IMessageHandler {

    private Game game;
    private static Gson gson = new Gson();
    private static final Logger logger = LoggerFactory.getLogger(GameMessageHandler.class);

    public GameMessageHandler(Game game) {
        this.game = game;
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
                game.setInputMouse(inputMouse);
                break;
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
            case UPDATE:
                updatePlayer(UPDATE);
                break;
            default:
                logger.error("No valid action");
        }
    }

    private void registerPlayer(Register register, String sessionId) {
        if (game.findPlayer(sessionId) == null) {
            Player player = new Player(register.getName(), sessionId);
            game.addPlayer(player);
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
            case UPDATE:

                break;
            default:
                throw new IllegalArgumentException("No direction found");
        }
    }
}
