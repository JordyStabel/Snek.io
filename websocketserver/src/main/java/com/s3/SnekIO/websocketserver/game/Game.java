package com.s3.SnekIO.websocketserver.game;

import com.s3.SnekIO.websocketserver.messagegenerator.IMessageGenerator;
import com.s3.SnekIO.websocketshared.models.InputMouse;
import com.s3.SnekIO.websocketshared.models.Orb;
import com.s3.SnekIO.websocketshared.models.Player;
import com.s3.SnekIO.websocketshared.models.Position;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(Game.class);

    private int height;
    private int width;

    private InputMouse inputMouse;

    private List<Player> players;
    private List<Orb> orbs;
    private boolean running = false;

    private Random random = new Random();

    private IMessageGenerator messageGenerator;
    private Thread gameThread;

    // GAME LOOP
    final double TICKS_PER_SECOND = 5;
    final double SKIP_TICKS = 1000 / TICKS_PER_SECOND;

    public Game(IMessageGenerator messageGenerator, int height, int width) {
        this.messageGenerator = messageGenerator;
        this.height = height;
        this.width = width;
        players = new ArrayList<>();
        orbs = new ArrayList<>();

        // Generate 100 orbs on game creation
        for (int i = 0; i < 200; i++) {
            orbs.add(new Orb(new Position((random.nextInt(3) - 1) * random.nextFloat() * 2000, (random.nextInt(3) - 1) * random.nextFloat() * 2000), 2));
        }
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

    public List<Orb> getOrbs() {
        return orbs;
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
            if (System.currentTimeMillis() > next_game_tick && players != null) {
                for (int i = 0; i < players.size(); i++) {
                    Player player = players.get(i);

                    // TODO: Gamelogic here

                    /** Loop through all players, check for each player Snek HEAD part if it's intersecting with any of the Snek parts of all other Sneks (players) A Snek is allowed to intersect with itself.
                     * First need to get the head part of the players Snek
                     * Than compare the position of that part with ALL other Snek part of the OTHER players
                     * TODO: In the future a quadtree could optimise this process
                     * */
                    // Player snek hasn't been created yet
                    if (!player.getSnek().getTail().isEmpty()) {
                        List<Position> playerSnek = player.getSnek().getTail();

                        // Last item in the playerSnek is the head
                        Position playerSnekHead = playerSnek.get(playerSnek.size() - 1);

                        for (Player otherPlayer : players) {
                            if (otherPlayer != player) {
                                for (Position otherPlayerSnekPart : otherPlayer.getSnek().getTail()) {
                                    // Replace '50' with actual r of players snek
                                    if (Math.hypot(playerSnekHead.getX() - otherPlayerSnekPart.getX(), playerSnekHead.getY() - otherPlayerSnekPart.getY()) < 50) {
                                        System.out.println("Player: " + player.getUuid() + " hit player: " + otherPlayer.getUuid());
                                        List<Position> toRemoveItems = playerSnek.subList(0, playerSnek.size() - 2);
                                        playerSnek.removeAll(toRemoveItems);
                                        player.getSnek().setSize(1);
                                    }
                                }
                            }
                        }
                    }
                }

                next_game_tick += SKIP_TICKS;
                messageGenerator.updatePlayers();
            }
        }
    }
}

