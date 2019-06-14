package com.s3.SnekIO.websocketserver.messagelogic;

import com.s3.SnekIO.websocketserver.endpoints.GameEndpoint;

public interface IGameMessageLogic {
    void setEndPoint(GameEndpoint gameEndpoint);
    void sendGlobalMessage(String msg);
}
