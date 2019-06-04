package com.s3.SnekIO.websocketshared.models;

public class Snek {
    private int x;
    private int y;
    private int r;

    private String uuid;

    public Snek(int x, int y, int r, String uuid) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.uuid = uuid;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getR() {
        return r;
    }

    public void setR(int r) {
        this.r = r;
    }
}
