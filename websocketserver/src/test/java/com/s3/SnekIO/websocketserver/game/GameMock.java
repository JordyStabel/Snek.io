package com.s3.SnekIO.websocketserver.game;

import com.s3.SnekIO.websocketshared.models.Orb;
import com.s3.SnekIO.websocketshared.models.Player;
import com.s3.SnekIO.websocketshared.models.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameMock {

    private List<Player> players;
    private List<Orb> orbs;

    private Random random = new Random();

    public GameMock() {
        players = new ArrayList<>();
        orbs = new ArrayList<>();
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

    public void addOrb(Orb orb) {
        orbs.add(orb);
    }

    public List<Orb> getOrbs() {
        return orbs;
    }

    public void addPlayer(Player player) {
        if (player != null) {
            players.add(player);
        }
    }

    public void removePlayer(String sessionId) {
        Player player = findPlayer(sessionId);
        if (player != null && player.equals(findPlayer(player.getSessionId()))) {
            // Add the Orbs back into the game
            explodeSnek(player.getSnek().getTail());
            players.remove(player);
        }
    }

    public boolean playerIntersect(Player player, Position playerSnekHead, List<Position> playerSnek, Player otherPlayer) {
        for (Position otherPlayerSnekPart : otherPlayer.getSnek().getTail()) {
            if (Math.hypot(playerSnekHead.getX() - otherPlayerSnekPart.getX(), playerSnekHead.getY() - otherPlayerSnekPart.getY()) < player.getSnek().getR() + otherPlayer.getSnek().getR()) {
                explodeSnek(playerSnek);
                return true;
            }
        }
        return false;
    }

    public void orbIntersect(Position playerSnekHead, Player player) {
        if (orbs.removeIf(orb -> Math.hypot(
                playerSnekHead.getX() - orb.getPosition().getX(),
                playerSnekHead.getY() - orb.getPosition().getY())
                < player.getSnek().getR() + orb.getValue())) {
            player.getSnek().increaseSize(1);

            orbs.add(new Orb(
                    new Position((random.nextInt(3) - 1) * random.nextFloat() * 2000,
                            (random.nextInt(3) - 1) * random.nextFloat() * 2000), 20));
        }
    }

    public void explodeSnek(List<Position> snek) {
        for (Position position : snek) {
            orbs.add(new Orb(new Position(
                    position.getX() + ((random.nextInt(3) - 1) * random.nextFloat() * 100), position.getY() + ((random.nextInt(3) - 1) * random.nextFloat() * 100)), 10));

        }
    }

    private void handleHitPlayer(List<Player> hitPlayers) {
        for (Player player : hitPlayers) {

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
    }
}
