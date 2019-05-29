package com.s3.SnekIO.websocketshared;

public class Test {

    private String string;
    private int number;

    public Test() {
    }

    public Test(String string, int number) {
        this.string = string;
        this.number = number;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "Test{" +
                "string='" + string + '\'' +
                ", number=" + number +
                '}';
    }
}

