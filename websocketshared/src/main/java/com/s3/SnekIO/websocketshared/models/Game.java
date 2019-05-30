package com.s3.SnekIO.websocketshared.models;

import com.s3.SnekIO.websocketshared.actions.IAction;
import com.s3.SnekIO.websocketshared.models.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Game implements IAction {
    private int height;
    private int width;

    private ArrayList<Player> players;

    public Game(int height, int width) {
        this.height = height;
        this.width = width;

        players = new ArrayList<>();
    }

    public List<Player> getPlayers() {
        return Collections.unmodifiableList(players);
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public void addPlayer(Player player) {
        if (player != null) {
            players.add(player);
        }
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    /**
     * Get a string with all game information, mainly for debugging
     *
     * @return String of game object
     */
    @Override
    public String toString() {
        return "Game{" +
                "height=" + height +
                ", width=" + width +
                ", players=" + players +
                '}';
    }
}
