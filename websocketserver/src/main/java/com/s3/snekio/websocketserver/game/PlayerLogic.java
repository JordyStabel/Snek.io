package com.s3.snekio.websocketserver.game;

import com.s3.snekio.websocketshared.models.Player;
import com.s3.snekio.websocketshared.models.Position;

public class PlayerLogic {

    private SnekLogic snekLogic = new SnekLogic();

    public void updatePlayerPosition(Player player, Position position) {
        Position old = player.getPosition();

        // Adding the new position from the client instead of setting it directly
        float x = old.getX() + position.getX();
        float y = old.getY() + position.getY();

        player.setPosition(new Position(x, y));

        // Update Snek
        snekLogic.updateSnek(player.getSnek(), x, y);
    }
}
