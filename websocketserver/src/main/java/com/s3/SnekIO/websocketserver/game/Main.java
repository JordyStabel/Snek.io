package com.s3.SnekIO.websocketserver.game;

import com.s3.SnekIO.websocketserver.endpoints.GameEndpoint;
import com.s3.SnekIO.websocketserver.messagegenerator.MessageGenerator;

public class Main {
    private static GameEndpoint gameEndpoint;

    public static void main(String[] args) {
        startGame();
    }

    private static void startGame(){
        MessageGenerator messageGenerator = new MessageGenerator(new GameEndpoint());
        Game game = new Game(messageGenerator, 500, 500);

        game.start();
    }
}
