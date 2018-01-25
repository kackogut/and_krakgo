package com.kacper.and_krakgo.model;

import io.reactivex.annotations.NonNull;

/**
 * Created by kacper on 20/01/2018.
 */

public class UserDetails {
    private String about_me;
    private Long dob_time;
    private String photo_url;
    private Long map_visibility;
    private Long longitude;
    private Long latitude;
    private String userID;
    private String display_name;

    public UserDetails(){
    }
    public UserDetails(String display_name, long dob_time, String photoUri){
        this.dob_time = dob_time;
        this.photo_url = photoUri;
        this.map_visibility = 0L;
        this.display_name = display_name;
    }

    public String getAbout_me() {
        return about_me;
    }

    @NonNull
    public Long getDob_time() {
        return dob_time;
    }

    public Long getMap_visibility() {
        return map_visibility;
    }

    public Long getLongitude() {
        return longitude;
    }

    public Long getLatitude() {
        return latitude;
    }

    public String getUserID() {
        return userID;
    }

    public void setAbout_me(String about_me) {
        this.about_me = about_me;
    }

    public void setDob_time(Long dob_time) {
        this.dob_time = dob_time;
    }

    public void setMap_visibility(Long map_visibility) {
        this.map_visibility = map_visibility;
    }

    public void setLongitude(Long longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(Long latitude) {
        this.latitude = latitude;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }
}
