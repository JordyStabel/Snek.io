package com.s3.SnekIO.websocketshared.models;

public interface IPlayer {
    String getSessionId();
    String getUuid();
    void setInputMouse(InputMouse inputMouse);
    Snek getSnek();
}
