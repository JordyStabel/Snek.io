package com.s3.SnekIO.websocketserver.game;

import com.s3.SnekIO.websocketserver.messagegenerator.IMessageGenerator;
import com.s3.SnekIO.websocketshared.models.InputMouse;
import com.s3.SnekIO.websocketshared.models.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class Game implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(Game.class);

    private int height;
    private int width;

    private InputMouse inputMouse;

    private List<Player> players;
    private boolean running = false;

    private IMessageGenerator messageGenerator;
    private Thread gameThread;

    private ExecutorService executorService;

    // GAME LOOP
    final double TICKS_PER_SECOND = 24;
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

    public Player findPlayer(String uuid) {
        for (Player player : players) {
            if (player.getUuid().equals(uuid)) {
                return player;
            }
        }
        return null;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public synchronized void addPlayer(Player player) {
        if (player != null) {
            players.add(player);
            logger.info("New player registered: {}", player);
        }
        logger.info("Players: {}", players);
        messageGenerator.updatePlayers();
    }

    public void removePlayer(String uuid) {
        Player player = findPlayer(uuid);
        removePlayer(player);
    }

    private void removePlayer(Player player) {
        if (player != null && player.equals(findPlayer(player.getUuid()))) {
            players.remove(player);
        }
    }

    public InputMouse getInputMouse() {
        return inputMouse;
    }

    public void setInputMouse(InputMouse inputMouse) {
        this.inputMouse = inputMouse;
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
