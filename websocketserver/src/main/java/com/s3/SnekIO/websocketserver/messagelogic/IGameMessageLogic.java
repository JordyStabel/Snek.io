package com.s3.SnekIO.websocketserver.messagelogic;

import com.s3.SnekIO.websocketserver.endpoints.TestEndpoint;

public interface IGameMessageLogic {
    void setEndPoint(TestEndpoint testEndpoint);
    void sendGlobalMessage(String msg);
}
