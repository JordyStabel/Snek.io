package com.s3.snekio.websocketshared.models;

import java.util.ArrayList;

public class Snek {
    private Position position;
    private float r;
    private int size;
    private ArrayList<Position> tail;
    private String uuid;


    public Snek(float x, float y, float r, int size, String uuid) {
        this.position = new Position(x, y);
        this.r = r;
        this.size = size;
        this.tail = new ArrayList<>();
        this.uuid = uuid;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void increaseSize(int increment) {
        size += increment;
    }

    public void decreaseSize(int decrement) {
        size += decrement;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public ArrayList<Position> getTail() {
        return tail;
    }

    public void setTail(ArrayList<Position> tail) {
        this.tail = tail;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public float getR() {
        return r;
    }

    public void setR(float r) {
        this.r = r;
    }
}
