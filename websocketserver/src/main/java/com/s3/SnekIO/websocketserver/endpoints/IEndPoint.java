package com.s3.SnekIO.websocketserver.endpoints;

import com.s3.SnekIO.websocketshared.util.IJson;

public interface IEndPoint {

    void sendTo(IJson json, String sessionId);

    void broadcast(IJson json);
}
