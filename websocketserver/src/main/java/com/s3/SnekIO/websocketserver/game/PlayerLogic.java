package com.s3.SnekIO.websocketserver.game;

import com.s3.SnekIO.websocketshared.models.InputMouse;
import com.s3.SnekIO.websocketshared.models.Player;

public class PlayerLogic {

    private SnekLogic snekLogic = new SnekLogic();

    public void updatePlayerInputMouse(Player player, InputMouse inputMouse) {
        InputMouse old = player.getInputMouse();

        // Adding the new position from the client instead of setting it directly
        float x = old.getX() + inputMouse.getX();
        float y = old.getY() + inputMouse.getY();

        player.setInputMouse(new InputMouse(x, y));

        // Update Snek
        snekLogic.updateSnek(player.getSnek(), x, y);
    }
}
