package com.s3.SnekIO.websocketshared.models;

import com.s3.SnekIO.websocketshared.actions.IAction;
import com.s3.SnekIO.websocketshared.util.IJson;

import java.util.Random;

public class Player implements IAction {

    private String name;
    private int x;
    private int y;
    private int r;

    private static Random random = new Random();

    public Player(String name) {
        this.name = name;
        this.x = random.nextInt(1700);
        this.y = random.nextInt(900);
        this.r = random.nextInt(125) + 25;
    }

    public String getName() {
        return name;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getR() {
        return r;
    }

    public void update(int x, int y, int r) {
        this.x = x;
        this.y = y;
        this.r = r;
    }
}
