package com.s3.SnekIO.websocketshared.models;

public class AIPlayer implements IPlayer {
    private String name;
    private String sessionId;
    private String uuid;
    private String color;
    private Snek snek;
    private InputMouse inputMouse;

    public AIPlayer(String name, String uuid, String color, Position startingPosition) {
        this.name = name;
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
        // Adding the new position from the client instead of setting it directly
        // TODO: Refactor this code to make it look decent
        float x = this.inputMouse.getX() + inputMouse.getX();
        float y = this.inputMouse.getY() + inputMouse.getY();

        this.inputMouse.setX(x);
        this.inputMouse.setY(y);

        // Update Snek
        snek.update(x, y);
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
                ", uuid='" + uuid + '\'' +
                ", color='" + color + '\'' +
                ", snek=" + snek +
                ", inputMouse=" + inputMouse +
                '}';
    }
}
