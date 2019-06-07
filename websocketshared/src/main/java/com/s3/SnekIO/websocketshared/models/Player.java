package com.s3.SnekIO.websocketshared.models;

import java.util.Arrays;
import java.util.Random;

public class Player {
    private String name;
    private String uuid;
    private Snek[] sneks;
    private InputMouse inputMouse;

    private Random rand = new Random();

    public Player(String name, String uuid) {
        this.name = name;
        this.uuid = uuid;
        this.inputMouse = new InputMouse(0, 0);
        sneks = new Snek[]{
                new Snek(0, 0, 35, rand.nextInt(999999999) + name + rand.nextInt(999999999)),
                new Snek(0, 0, 35, rand.nextInt(999999999) + name + rand.nextInt(999999999)),
                new Snek(0, 0, 35, rand.nextInt(999999999) + name + rand.nextInt(999999999)),
                new Snek(0, 0, 35, rand.nextInt(999999999) + name + rand.nextInt(999999999)),
                new Snek(0, 0, 35, rand.nextInt(999999999) + name + rand.nextInt(999999999)),
                new Snek(0, 0, 35, rand.nextInt(999999999) + name + rand.nextInt(999999999)),
                new Snek(0, 0, 35, rand.nextInt(999999999) + name + rand.nextInt(999999999)),
                new Snek(0, 0, 35, rand.nextInt(999999999) + name + rand.nextInt(999999999)),
                new Snek(0, 0, 35, rand.nextInt(999999999) + name + rand.nextInt(999999999)),
                new Snek(0, 0, 35, rand.nextInt(999999999) + name + rand.nextInt(999999999)),
        };
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
        // This is the old way, new way should add the position instead of setting it completely
        //this.inputMouse = inputMouse;

        // Adding the new position from the client instead of setting it directly
        // TODO: Refactor this code to make it look decent
        int x = this.inputMouse.getX() + inputMouse.getX();
        int y = this.inputMouse.getY() + inputMouse.getY();

        this.inputMouse.setX(x);
        this.inputMouse.setY(y);

        this.sneks[this.sneks.length + 1] = new Snek(0, 0, 35, rand.nextInt(999999999) + name + rand.nextInt(999999999));

        dragSnek(0, x, y);
        for (int i = 0; i < sneks.length - 1; i++) {
            dragSnek(i + 1, sneks[i].getX(), sneks[i].getY());
        }
    }

    private void dragSnek(int i, int x, int y) {
        float dx = x - sneks[i].getX();
        float dy = y - sneks[i].getY();
        float angle = (float) Math.atan2(dy, dx);
        sneks[i].setX((int) (x - Math.cos(angle) * 25));
        sneks[i].setY((int) (y - Math.sin(angle) * 25));
    }

    public Snek[] getSneks() {
        return sneks;
    }

    public void setSneks(Snek[] sneks) {
        this.sneks = sneks;
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", uuid='" + uuid + '\'' +
                ", sneks=" + Arrays.toString(sneks) +
                ", inputMouse=" + inputMouse +
                '}';
    }
}
