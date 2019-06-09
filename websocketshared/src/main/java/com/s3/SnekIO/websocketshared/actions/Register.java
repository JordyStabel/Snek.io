package com.s3.SnekIO.websocketshared.actions;

import com.s3.SnekIO.websocketshared.models.Position;

public class Register implements IAction {
    private String name;
    private String color; // Hex color value
    private Position startingPosition;

    public Register(String name, String color, Position startingPosition) {
        this.name = name;
        this.color = color;
        this.startingPosition = startingPosition;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public Position getStartingPosition() {
        return startingPosition;
    }

    public void setStartingPosition(Position startingPosition) {
        this.startingPosition = startingPosition;
    }
}
