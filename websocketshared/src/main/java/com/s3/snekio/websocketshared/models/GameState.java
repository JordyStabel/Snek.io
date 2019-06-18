package com.s3.snekio.websocketshared.models;

import com.s3.snekio.websocketshared.actions.IAction;
import java.util.List;

public class GameState implements IAction {
    private List<Player> players;
    private List<Orb> orbs;

    public GameState(List<Orb> orbs, List<Player> players) {
        this.players = players;
        this.orbs = orbs;
    }
}
