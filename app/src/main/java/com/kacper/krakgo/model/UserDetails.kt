package com.kacper.krakgo.model

import android.os.Parcel
import android.os.Parcelable

import java.util.Calendar

import io.reactivex.annotations.NonNull

/**
 * Created by kacper on 20/01/2018.
 */

class UserDetails : Parcelable {
    var about_me: String? = null
    var dob_time: Long? = 0L
    var photo_url: String? = null
    var map_visibility: Long? = 0L
    var longitude: Double? = 0.0
    var latitude: Double? = 0.0
    var userID: String? = null
    var display_name: String? = null
    var last_login_time: Long? = null

    constructor() {}

    override fun writeToParcel(parcel: Parcel, i: Int) {
        parcel.writeStringArray(arrayOf<String>(about_me!!, photo_url!!, userID!!, display_name!!))
        parcel.writeDoubleArray(doubleArrayOf(longitude!!, latitude!!))
        parcel.writeLongArray(longArrayOf(dob_time!!, map_visibility!!, last_login_time!!))
    }

    constructor(`in`: Parcel) {
        val strings = arrayOfNulls<String>(4)
        val doubles = DoubleArray(2)
        val longs = LongArray(3)

        `in`.readStringArray(strings)
        `in`.readDoubleArray(doubles)
        `in`.readLongArray(longs)

        this.about_me = strings[0]
        this.photo_url = strings[1]
        this.userID = strings[2]
        this.display_name = strings[3]

        this.dob_time = longs[0]
        this.map_visibility = longs[1]
        this.last_login_time = longs[2]

        this.longitude = doubles[0]
        this.latitude = doubles[1]
    }

    constructor(display_name: String?, dob_time: Long, photoUri: String) {
        this.dob_time = dob_time
        this.photo_url = photoUri
        this.display_name = display_name
        this.about_me = ""
        this.last_login_time = Calendar.getInstance().timeInMillis
    }

    constructor(photo_url: String, userID: String, display_name: String) {
        this.photo_url = photo_url
        this.userID = userID
        this.display_name = display_name
        this.last_login_time = Calendar.getInstance().timeInMillis
    }

    constructor(about_me: String, dob_time: Long, photo_url: String,
                map_visibility: Long, longitude: Double, latitude: Double,
                userID: String, display_name: String, conversations: List<String>) {
        this.about_me = about_me
        this.dob_time = dob_time
        this.photo_url = photo_url
        this.map_visibility = map_visibility
        this.longitude = longitude
        this.latitude = latitude
        this.userID = userID
        this.display_name = display_name
    }


    override fun describeContents(): Int {
        return 0
    }

    fun setLast_login_time() {
        this.last_login_time = Calendar.getInstance().timeInMillis
    }

    companion object {
        @JvmField val CREATOR = object : Parcelable.Creator<UserDetails> {
            override fun createFromParcel(parcel: Parcel) = UserDetails(parcel)

            override fun newArray(size: Int) = arrayOfNulls<UserDetails>(size)
        }
    }
}
