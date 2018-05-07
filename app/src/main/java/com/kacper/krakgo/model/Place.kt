package com.kacper.krakgo.model

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by kacper on 10/03/2018.
 */

class Place : Parcelable {
    var latitude: Double = -1.0
    var longitude: Double = -1.0
    lateinit var display_name: String
    lateinit var photo_url: String
    lateinit var reviewsID: String

    constructor() {
        //Constructor needed for firebase data parse
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, i: Int) {
        parcel.writeDoubleArray(doubleArrayOf(latitude, longitude))
        parcel.writeStringArray(arrayOf(display_name, photo_url, reviewsID))
    }

    constructor(`in`: Parcel) {
        val strings = arrayOfNulls<String>(3)
        val doubles = DoubleArray(2)

        `in`.readDoubleArray(doubles)
        `in`.readStringArray(strings)

        this.display_name = strings[0]!!
        this.photo_url = strings[1]!!
        this.reviewsID = strings[2]!!

        this.latitude = doubles[0]
        this.longitude = doubles[1]
    }

    companion object {
        @JvmField val CREATOR = object : Parcelable.Creator<Place> {
            override fun createFromParcel(parcel: Parcel) = Place(parcel)

            override fun newArray(size: Int) = arrayOfNulls<Place>(size)
        }
    }
}
