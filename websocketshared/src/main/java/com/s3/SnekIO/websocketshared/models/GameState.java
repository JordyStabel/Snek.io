package com.s3.SnekIO.websocketshared.models;

import com.s3.SnekIO.websocketshared.actions.IAction;
import java.util.List;

public class GameState implements IAction {
    private List<Player> players;

    public GameState(List<Player> players) {
        this.players = players;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public void addPlayer(Player player) {
        if (player != null) {
            players.add(player);
        }
    }

    /**
     * Get a string with all game information, mainly for debugging
     *
     * @return String of game object
     */
    @Override
    public String toString() {
        return "GameState{" +
                "players=" + players +
                '}';
    }
}
