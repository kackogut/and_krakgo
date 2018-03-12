package com.kacper.and_krakgo.model;

/**
 * Created by kacper on 10/03/2018.
 */

public class Place {
    private Double latitude;
    private Double longitude;
    private String display_name;
    private String photo_url;
    private String reviewsID;

    public Place() {
    }

    public Place(Double latitude, Double longitude, String display_name, String photo_url, String reviewsID) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.display_name = display_name;
        this.photo_url = photo_url;
        this.reviewsID = reviewsID;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public String getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
    }

    public String getReviewsID() {
        return reviewsID;
    }

    public void setReviewsID(String reviewsID) {
        this.reviewsID = reviewsID;
    }
}
