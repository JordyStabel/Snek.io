package com.s3.SnekIO.game;

import com.s3.SnekIO.websocketserver.endpoints.TestEndpoint;

public class Main {
    private static TestEndpoint testEndPoint;

    public static void main(String[] args) {
        startGame();
    }

    private static void startGame(){
        Game game = new Game(500, 500);

        testEndPoint = new TestEndpoint(null, null);
    }
}
