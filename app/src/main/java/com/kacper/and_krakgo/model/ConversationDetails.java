package com.kacper.and_krakgo.model;

import io.reactivex.annotations.Nullable;

/**
 * Created by kacper on 07/02/2018.
 */

public class ConversationDetails {
    private String conversationID;
    private String user_avatar;
    private String user_name;
    private Long time;
    @Nullable
    private String user_id;

    public ConversationDetails() {
    }

    public ConversationDetails(String conversationID, String user_avatar, String user_name, Long time) {
        this.conversationID = conversationID;
        this.user_avatar = user_avatar;
        this.user_name = user_name;
        this.time = time;
    }

    public String getConversationID() {
        return conversationID;
    }

    public void setConversationID(String conversationID) {
        this.conversationID = conversationID;
    }

    public String getUser_avatar() {
        return user_avatar;
    }

    public void setUser_avatar(String user_avatar) {
        this.user_avatar = user_avatar;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
