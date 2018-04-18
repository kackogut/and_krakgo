package com.kacper.krakgo.model;

import com.google.firebase.auth.FirebaseUser;
import com.kacper.krakgo.KrakGoApp;

import java.util.Date;

/**
 * Created by kacper on 25/01/2018.
 */

public class ForumMessage {
    private String message_text;
    private String userID;
    private Long time;
    private String userName;
    private String avatarUrl;

    public ForumMessage() {
    }

    public ForumMessage(String message_text, FirebaseUser currentUser) {
        this.message_text = message_text;
        this.userID = currentUser.getUid();
        this.userName = currentUser.getDisplayName();
        this.avatarUrl = currentUser.getPhotoUrl().toString();
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

    public String getUserName() {
        return userName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }
}
