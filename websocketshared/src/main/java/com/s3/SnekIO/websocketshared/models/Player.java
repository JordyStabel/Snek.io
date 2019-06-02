package com.s3.SnekIO.websocketshared.models;

public class Player {
    private String name;
    private String uuid;
    private InputMouse inputMouse;

    public Player(String name, String uuid) {
        this.name = name;
        this.uuid = uuid;
        this.inputMouse = new InputMouse(0,0);
    }

    public String getName() {
        return name;
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

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", uuid='" + uuid + '\'' +
                ", inputMouse=" + inputMouse +
                '}';
    }
}
