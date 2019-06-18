package com.s3.snekio.websocketshared.message;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.s3.snekio.websocketshared.actions.Actions;
import com.s3.snekio.websocketshared.actions.IAction;
import com.s3.snekio.websocketshared.util.IJson;

public class Message implements IJson {

    private Actions action;
    private String content;
    private IAction data;

    private static final Gson gson = new Gson();

    public Message(Actions action) {
        this.action = action;
    }

    public Message(Actions action, IAction data) {
        this.action = action;
        this.content = gson.toJson(data);
    }

    public Actions getAction() {
        return action;
    }

    public String getContent() {
        return content;
    }

    public IAction getData() {
        return data;
    }

    public IAction parseData(Class<? extends IAction> iAction) {
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = jsonParser.parse(content).getAsJsonObject();
        this.data = gson.fromJson(jsonObject, iAction);
        return this.data;
    }

    @Override
    public String toJson() {
        return gson.toJson(this);
    }

    /**
     * Get a string with all message information, mainly for debugging
     *
     * @return String of message object
     */
    @Override
    public String toString() {
        return "Message{" +
                "action=" + action +
                ", content='" + content + '\'' +
                ", data=" + data +
                '}';
    }
}
