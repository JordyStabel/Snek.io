package com.s3.SnekIO.websocketserver.util;

import com.google.gson.Gson;
import com.s3.SnekIO.websocketserver.WebsocketServer;
import com.s3.SnekIO.websocketserver.messages.GlobalGameMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageHelper {

    private static final Logger logger = LoggerFactory.getLogger(MessageHelper.class);

    private static final Gson gson = new Gson();

    public static Object getGlobalMessageString(String msg) {
        logger.info("Message: {}", msg);
        return new GlobalGameMessage(msg);
    }
}
