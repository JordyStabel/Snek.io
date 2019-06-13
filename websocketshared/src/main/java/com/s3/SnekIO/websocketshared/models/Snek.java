package com.s3.SnekIO.websocketshared.models;

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

    public void update(float x, float y) {
        // Add new position to tail
        tail.add(new Position(position.getX() + x, position.getY() + y));

        // Check if the max length has been reached, otherwise remove last position (very last object in the Snek) which is the first in the array
        if (tail.size() > size) {
            tail.remove(0);
        }
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

    public void setR(int r) {
        this.r = r;
    }
}
