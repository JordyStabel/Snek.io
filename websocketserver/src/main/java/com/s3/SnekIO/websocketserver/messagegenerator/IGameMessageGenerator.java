package com.s3.SnekIO.websocketserver.messagegenerator;

public interface IGameMessageGenerator {

    /**
     * Create a Message for a specific target session to receive
     * @param action action
     * @param object payload
     * @param targetSessionId target
     */
    void MessageConstructor(String action, String object, String targetSessionId);

    /**
     * Create a Message for all sessions to receive
     * @param action action
     * @param object payload
     */
    void GlobalMessageConstructor(String action, Object object);


}
