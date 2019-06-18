package com.s3.snekio.websocketserver.endpoints;

import com.s3.snekio.websocketshared.util.IJson;

public interface IEndPoint {

    void sendTo(IJson json, String sessionId);

    void broadcast(IJson json);
}
