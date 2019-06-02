package com.s3.SnekIO.websocketserver.messagegenerator;

import com.s3.SnekIO.websocketserver.game.Game;

public interface IMessageGenerator {

    void updatePlayers();

    void setGame(Game game);
}