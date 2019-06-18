package com.s3.SnekIO.websocketserver.messageHandlers;

import com.google.gson.Gson;
import com.s3.SnekIO.websocketserver.game.Game;
import com.s3.SnekIO.websocketserver.game.PlayerLogic;
import com.s3.SnekIO.websocketserver.util.IObservable;
import com.s3.SnekIO.websocketserver.util.IObserver;
import com.s3.SnekIO.websocketshared.models.Player;
import com.s3.SnekIO.websocketshared.models.Position;
import com.s3.SnekIO.websocketshared.models.Register;
import com.s3.SnekIO.websocketshared.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class GameMessageHandler implements IMessageHandler, IObservable {

    private Game game;

    private ArrayList<IObserver> observers;

    private PlayerLogic playerLogic;
    private static Gson gson = new Gson();
    private static final Logger logger = LoggerFactory.getLogger(GameMessageHandler.class);

    public GameMessageHandler(Game game) {
        this.game = game;
        playerLogic = new PlayerLogic();
        this.observers = new ArrayList<>();
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
                Position position = (Position) message.parseData(Position.class);
                Player player = game.findPlayer(sessionId);

                if (player == null) {
                    logger.error("PlayerLogic can't be null");
                    return;
                }
                playerLogic.updatePlayerPosition(player, position);
                break;
            default:
                logger.error("No valid action");
        }
    }

    private void registerPlayer(Register register, String sessionId) {
        if (game.findPlayer(sessionId) == null) {
            Player player = new Player(register.getName(), sessionId, register.getUuid(), register.getColor(), register.getStartingPosition());
            game.addPlayer(player);
            notifyAllObservers();
        }
    }

    @Override
    public void disconnected(String sessionId) {
        game.removePlayer(sessionId);
        notifyAllObservers();
    }

    @Override
    public void subscribe(IObserver observer) {
        observers.add(observer);
    }

    @Override
    public void unsubscribe(IObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyAllObservers() {
        try {
            for (IObserver observer : observers) {
                observer.update(this);
            }
        } catch(Exception e) {
            logger.error("Couldn't notify observers: {0}", e);
        }
    }
}
