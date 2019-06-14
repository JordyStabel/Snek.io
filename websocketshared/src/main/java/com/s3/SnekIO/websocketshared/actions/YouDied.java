package com.s3.SnekIO.websocketshared.actions;

import com.s3.SnekIO.websocketshared.models.Position;

public class YouDied implements IAction {
    private String name;
    private String color;
    private String uuid;
    private Position startingPosition;

    public YouDied(String name, String color, String uuid, Position startingPosition) {
        this.name = name;
        this.color = color;
        this.uuid = uuid;
        this.startingPosition = startingPosition;
    }

    public String getName() {
        return name;
    }

    public String getUuid() {
        return uuid;
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
