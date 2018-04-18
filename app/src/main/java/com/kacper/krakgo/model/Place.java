package com.kacper.krakgo.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by kacper on 10/03/2018.
 */

public class Place implements Parcelable{
    private Double latitude;
    private Double longitude;
    private String display_name;
    private String photo_url;
    private String reviewsID;

    public Place() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeDoubleArray(new double[]{
                latitude,
                longitude
        });
        parcel.writeStringArray(new String[]{
                display_name,
                photo_url,
                reviewsID
        });
    }
    public Place(Parcel in){
        String[] strings = new String[3];
        double[] doubles = new double[2];

        in.readDoubleArray(doubles);
        in.readStringArray(strings);

        this.display_name = strings[0];
        this.photo_url = strings[1];
        this.reviewsID = strings[2];

        this.latitude = doubles[0];
        this.longitude = doubles[1];
    }
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Place createFromParcel(Parcel in) {
            return new Place(in);
        }

        public Place[] newArray(int size) {
            return new Place[size];
        }
    };

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
