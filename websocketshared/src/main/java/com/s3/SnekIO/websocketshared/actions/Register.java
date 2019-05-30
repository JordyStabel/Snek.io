package com.s3.SnekIO.websocketshared.actions;

public class Register implements IAction {
    private String name;
    private String color; // Hex color value

    public Register(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }
}
