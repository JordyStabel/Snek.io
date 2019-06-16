package com.s3.SnekIO.websocketserver.game;

import com.s3.SnekIO.websocketserver.messagegenerator.IMessageGenerator;
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

    private List<Player> players;
    private List<Orb> orbs;
    private boolean running = false;

    private Random random = new Random();

    private IMessageGenerator messageGenerator;
    private Thread gameThread;

    // GAME LOOP
    private final double TICKS_PER_SECOND = 5;
    private final double SKIP_TICKS = 1000 / TICKS_PER_SECOND;

    public Game(IMessageGenerator messageGenerator, int height, int width) {
        this.messageGenerator = messageGenerator;
        this.height = height;
        this.width = width;
        players = new ArrayList<>();
        orbs = new ArrayList<>();

        // Generate 100 orbs on game creation
        for (int i = 0; i < 200; i++) {
            orbs.add(new Orb(new Position((random.nextInt(3) - 1) * random.nextFloat() * 2000, (random.nextInt(3) - 1) * random.nextFloat() * 2000), 10));
        }
    }

    public void start() {
        running = true;

        gameThread = new Thread(this);
        gameThread.start();
    }

    public Player findPlayer(String sessionId) {
        for (Player player : players) {
            if (player.getSessionId().equals(sessionId)) {
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

    public void addPlayer(Player player) {
        if (player != null) {
            players.add(player);
            logger.info("New player registered: {}", player);
        }
        logger.info("Players: {}", players);
        messageGenerator.updatePlayers();
    }

    public void removePlayer(String sessionId) {
        Player player = findPlayer(sessionId);
        if (player != null && player.equals(findPlayer(player.getSessionId()))) {
            // Add the Orbs back into the game
            explodeSnek(player.getSnek().getTail());
            players.remove(player);
        }
    }

    private boolean playerIntersect(Player player, Position playerSnekHead, List<Position> playerSnek, Player otherPlayer) {
        for (Position otherPlayerSnekPart : otherPlayer.getSnek().getTail()) {
            if (Math.hypot(playerSnekHead.getX() - otherPlayerSnekPart.getX(), playerSnekHead.getY() - otherPlayerSnekPart.getY()) < player.getSnek().getR() + otherPlayer.getSnek().getR()) {
                logger.info("Player: {} hit player: {}", player.getUuid(), otherPlayer.getUuid());
                explodeSnek(playerSnek);
                return true;
            }
        }
        return false;
    }

    private void orbIntersect(Position playerSnekHead, Player player) {
        // Check for orbs collecting
        // Check each orb position if it's intersecting with the Snek head
        // New java method (Java 8 "new"), which removes an item from a list if it matches a condition.
        // Without needing to create a loop AND no ConcurrentModificationException!
        // It also return a boolean, so it's easy to do something else after meeting the condition
        if (orbs.removeIf(orb -> Math.hypot(
                playerSnekHead.getX() - orb.getPosition().getX(),
                playerSnekHead.getY() - orb.getPosition().getY())
                < player.getSnek().getR() + orb.getValue())) {
            player.getSnek().increaseSize(1);

            // Add a new orb back into the game (bigger so it's easier to see the newly added ones)
            orbs.add(new Orb(
                    new Position((random.nextInt(3) - 1) * random.nextFloat() * 2000,
                            (random.nextInt(3) - 1) * random.nextFloat() * 2000), 20));
        }
    }

    private void explodeSnek(List<Position> snek) {
        for (Position position : snek) {
            try {
                // Add the parts of the destroyed Snek, back as Orbs in a semi random location
                // Add a new Orb around a 200 radius of the origin
                orbs.add(new Orb(new Position(
                        position.getX() + ((random.nextInt(3) - 1) * random.nextFloat() * 100), position.getY() + ((random.nextInt(3) - 1) * random.nextFloat() * 100)), 10));
            } catch (Exception e) {
                logger.error("ConcurrentModificationException");
            }
        }
    }

    private void handleHitPlayer(List<Player> hitPlayers) {
        for (Player player : hitPlayers) {
            System.out.println(player.getName());
            messageGenerator.sendToPlayer(player.getSessionId());
        }
    }

    private void gameUpdateTick() {
        ArrayList<Player> hitPlayers = new ArrayList<>();
        for (Player player : players) {

            /** Loop through all players, check for each player Snek HEAD part if it's intersecting with any of the Snek parts of all other Sneks (players) A Snek is allowed to intersect with itself.
             * First need to get the head part of the players Snek
             * Than compare the position of that part with ALL other Snek part of the OTHER players
             * TODO: In the future a quadtree could optimise this process
             * */
            // Players snek hasn't been created yet
            if (!player.getSnek().getTail().isEmpty()) {
                List<Position> playerSnek = player.getSnek().getTail();

                // Last item in the playerSnek is the head
                Position playerSnekHead = playerSnek.get(playerSnek.size() - 1);

                // Check other player collision
                for (Player otherPlayer : players) {
                    if (otherPlayer != player && playerIntersect(player, playerSnekHead, playerSnek, otherPlayer)) {
                        hitPlayers.add(player);

                        // Player is going to reset on the client anyways, but this would be useful it was handled a better way
                        playerSnek.clear();
                        player.getSnek().setSize(2);
                    }
                }

                // Check for collecting Orbs
                orbIntersect(playerSnekHead, player);
            }
        }
        handleHitPlayer(hitPlayers);
        hitPlayers.clear();
        messageGenerator.updatePlayers();
    }

    @Override
    public void run() {
        double nextGameTick = System.currentTimeMillis();
        while (running) {
            if (System.currentTimeMillis() > nextGameTick && players != null) {
                gameUpdateTick();
                nextGameTick += SKIP_TICKS;
            }
        }
    }
}
