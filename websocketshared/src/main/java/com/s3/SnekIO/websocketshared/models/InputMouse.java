package com.s3.SnekIO.websocketshared.models;

import com.s3.SnekIO.websocketshared.actions.IAction;

public class InputMouse implements IAction {
    private float x;
    private float y;

    public InputMouse(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "InputMouse{" + '\'' +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}
