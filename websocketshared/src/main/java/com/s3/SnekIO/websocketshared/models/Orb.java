package com.s3.SnekIO.websocketshared.models;

import java.awt.*;

public class Orb {
    private Position position;

    private float value;
    private Color color;

    public Orb(Position position, float value) {
        this.position = position;
        this.value = value;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Orb{" +
                "position=" + position +
                ", value=" + value +
                '}';
    }
}
