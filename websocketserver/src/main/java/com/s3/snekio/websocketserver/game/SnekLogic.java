package com.s3.snekio.websocketserver.game;

import com.s3.snekio.websocketshared.models.Position;
import com.s3.snekio.websocketshared.models.Snek;

import java.util.ArrayList;

public class SnekLogic {

    public void updateSnek(Snek snek, float x, float y) {

        Position oldPosition = snek.getPosition();
        ArrayList<Position> tail = snek.getTail();
        int size = snek.getSize();

        float oldX = oldPosition.getX();
        float oldY = oldPosition.getY();

        // Add new position to tail
        tail.add(new Position(oldX + x, oldY + y));

        // Increase radius
        snek.setR((float) (Math.sqrt(size) * 10));

        // Check if the max length has been reached, otherwise remove last position (very last object in the Snek) which is the first in the array
        if (tail.size() > size) {
            tail.remove(0);
        }
        snek.setTail(tail);
    }
}
