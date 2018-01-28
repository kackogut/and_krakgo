package com.kacper.and_krakgo.model;

import java.util.Date;

/**
 * Created by kacper on 25/01/2018.
 */

public class ForumMessage {
    private String message_text;
    private String userID;
    private Long time;

    public ForumMessage() {
    }

    public ForumMessage(String message_text, String userID) {
        this.message_text = message_text;
        this.userID = userID;
        this.time = new Date().getTime();
    }

    public String getMessage_text() {
        return message_text;
    }

    public void setMessage_text(String message_text) {
        this.message_text = message_text;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }
}
