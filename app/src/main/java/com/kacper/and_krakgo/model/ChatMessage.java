package com.kacper.and_krakgo.model;

/**
 * Created by kacper on 04/02/2018.
 */

public class ChatMessage {
    private String user_id;
    private Long time;
    private String message;

    public ChatMessage(String user_id, Long time, String message) {
        this.user_id = user_id;
        this.time = time;
        this.message = message;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
