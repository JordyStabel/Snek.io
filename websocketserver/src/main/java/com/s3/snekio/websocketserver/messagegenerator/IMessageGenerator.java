package com.s3.snekio.websocketserver.messagegenerator;

import com.s3.snekio.websocketserver.game.Game;

public interface IMessageGenerator {

    void updatePlayers();

    void sendToPlayer(String sessionId);

    void setGame(Game game);
}
