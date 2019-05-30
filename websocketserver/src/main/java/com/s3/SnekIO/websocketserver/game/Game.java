package com.s3.SnekIO.websocketserver.game;


import com.s3.SnekIO.websocketserver.messagegenerator.IMessageGenerator;
import com.s3.SnekIO.websocketshared.models.Player;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;

public class Game implements Runnable {
    private int height;
    private int width;

    private ArrayList<Player> players;
    private boolean running = false;

    private IMessageGenerator messageGenerator;
    private Thread gameThread;

    private ExecutorService executorService;

    // GAME LOOP
    final double TICKS_PER_SECOND = 1;
    final double SKIP_TICKS = 1000 / TICKS_PER_SECOND;

    public Game(IMessageGenerator messageGenerator, int height, int width) {
        this.messageGenerator = messageGenerator;
        this.height = height;
        this.width = width;
        players = new ArrayList<>();
    }

    public void start() {
        running = true;

        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double next_game_tick = System.currentTimeMillis();
        while (running) {
            if (System.currentTimeMillis() > next_game_tick) {
//                for (int i = 0; i < players.size(); i++) {
//                    Player player = players.get(i);
//
//                    // TODO: Something here
//                }
                next_game_tick += SKIP_TICKS;
                messageGenerator.updatePlayers();
            }
        }
    }
}
