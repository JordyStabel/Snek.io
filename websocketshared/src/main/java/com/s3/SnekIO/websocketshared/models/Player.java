package com.s3.SnekIO.websocketshared.models;

import java.util.ArrayList;
import java.util.Random;

public class Player {
    private String name;
    private String uuid;
    private Snek snek;
    private InputMouse inputMouse;

    private static Random rand = new Random();
    private int count = 0;

    public Player(String name, String uuid, Position startingPosition) {
        this.name = name;
        this.uuid = uuid;
        this.inputMouse = new InputMouse(0, 0);
        this.snek = new Snek(startingPosition.getX(), startingPosition.getY(), 25, 25, rand.nextInt(99999) + name + rand.nextInt(99999));
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
        float x = this.inputMouse.getX() + inputMouse.getX();
        float y = this.inputMouse.getY() + inputMouse.getY();

        this.inputMouse.setX(x);
        this.inputMouse.setY(y);

        // Update Snek
        snek.update(x, y);

//        dragSnek(0, x, y);
//        for (int i = 0; i < sneks.size() - 1; i++) {
//            dragSnek(i + 1, sneks.get(i).getX(), sneks.get(i).getY());
//        }
    }

//    public void updateSnekPos() {
//        // Update first Snek with new mouse position
//        updatePrevPos(this.inputMouse.getX(), this.inputMouse.getY(), sneks.get(0));
//
//        // Set the position of each Snek equal to the position of the previous one
//        for (int i = 1; i < sneks.size() - 1; i++) {
//            Snek leadingSnek = sneks.get(i - 1);
//            updatePrevPos(leadingSnek.getPrevX(), leadingSnek.getPrevY(), sneks.get(i));
//        }
//    }

//    private void dragSnek(int i, int x, int y) {
//        float dx = x - sneks.get(i).getX();
//        float dy = y - sneks.get(i).getY();
//        float angle = (float) Math.atan2(dy, dx);
//        sneks.get(i).setX((int) (x - Math.cos(angle) * 25));
//        sneks.get(i).setY((int) (y - Math.sin(angle) * 25));
//    }

//    private void updatePrevPos(int x, int y, Snek snek) {
//        if (count >= 100) {
//            sneks.add(new Snek(25, 25, 35, 0, 0, rand.nextInt(999999999) + name + rand.nextInt(999999999)));
//            count = 0;
//        }
//
//        snek.setPrevX(snek.getX());
//        snek.setPrevY(snek.getY());
//        if (x == snek.getX() || y == snek.getY()) {
//            count++;
//         return;
//        }
//        snek.setX(x);
//        snek.setY(y);
//
//        count++;
//    }


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
                ", snek=" + snek +
                ", inputMouse=" + inputMouse +
                '}';
    }
}
