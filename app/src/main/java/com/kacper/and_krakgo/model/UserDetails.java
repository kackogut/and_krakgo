package com.kacper.and_krakgo.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;

/**
 * Created by kacper on 20/01/2018.
 */

public class UserDetails implements Parcelable{
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
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStringArray(new String[]{
                about_me,
                photo_url,
                userID,
                display_name
        });
        parcel.writeLongArray(new long[]{
                dob_time,
                map_visibility,
                longitude,
                latitude
        });
    }
    public UserDetails(Parcel in){
        String[] strings = new String[4];
        long[] longs = new long[4];

        in.readStringArray(strings);
        in.readLongArray(longs);

        this.about_me = strings[0];
        this.photo_url = strings[1];
        this.userID = strings[2];
        this.display_name = strings[3];

        this.dob_time = longs[0];
        this.map_visibility = longs[1];
        this.longitude = longs[2];
        this.latitude = longs[3];
    }
    public UserDetails(String display_name, long dob_time, String photoUri){
        this.dob_time = dob_time;
        this.photo_url = photoUri;
        this.map_visibility = 0L;
        this.display_name = display_name;
        this.latitude = 0L;
        this.longitude = 0L;
        this.about_me = "";
    }

    public UserDetails(String about_me, Long dob_time, String photo_url, Long map_visibility, Long longitude, Long latitude, String userID, String display_name, List<String> conversations) {
        this.about_me = about_me;
        this.dob_time = dob_time;
        this.photo_url = photo_url;
        this.map_visibility = map_visibility;
        this.longitude = longitude;
        this.latitude = latitude;
        this.userID = userID;
        this.display_name = display_name;
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public UserDetails createFromParcel(Parcel in) {
            return new UserDetails(in);
        }

        public UserDetails[] newArray(int size) {
            return new UserDetails[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }


}
