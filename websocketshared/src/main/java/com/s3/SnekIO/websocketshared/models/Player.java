package com.s3.SnekIO.websocketshared.models;

import java.util.Random;

public class Player {
    private String name;
    private String sessionId;
    private String uuid;
    private String color;
    private Snek snek;
    private InputMouse inputMouse;

    public Player(String name, String sessionId, String uuid, String color, Position startingPosition) {
        this.name = name;
        this.sessionId = sessionId;
        this.uuid = uuid;
        this.color = color;
        this.inputMouse = new InputMouse(0, 0);
        this.snek = new Snek(startingPosition.getX(), startingPosition.getY(), 25, 5, uuid);
    }

    public String getName() {
        return name;
    }

    public String getSessionId() {
        return sessionId;
    }

    public String getUuid() {
        return uuid;
    }

    public InputMouse getInputMouse() {
        return inputMouse;
    }

    public void setInputMouse(InputMouse inputMouse) {
        this.inputMouse = inputMouse;
    }

    public Snek getSnek() {
        return snek;
    }

    public void setSnek(Snek snek) {
        this.snek = snek;
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", sessionId='" + sessionId + '\'' +
                ", uuid='" + uuid + '\'' +
                ", color='" + color + '\'' +
                ", snek=" + snek +
                ", inputMouse=" + inputMouse +
                '}';
    }
}
