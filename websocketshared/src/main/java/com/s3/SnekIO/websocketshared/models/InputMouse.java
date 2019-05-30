package com.s3.SnekIO.websocketshared.models;

import com.s3.SnekIO.websocketshared.actions.IAction;

public class InputMouse implements IAction {
    private int x;
    private int y;

    public InputMouse(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "InputMouse{" + '\'' +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}
