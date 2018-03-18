package com.kacper.and_krakgo.screens.home.map

import android.location.Location
import com.kacper.and_krakgo.model.Place
import com.kacper.and_krakgo.model.UserDetails
import com.kacper.and_krakgo.mvp.MvpPresenter
import com.kacper.and_krakgo.mvp.MvpView
import com.kacper.and_krakgo.screens.home.profile.ProfileContract

/**
 * Created by kacper on 10/03/2018.
 */
object MapContract {
    interface View : MvpView {
        fun setPlaces(places:ArrayList<Place>)
        fun setUsers(users:ArrayList<UserDetails>)
    }
    interface Presenter : MvpPresenter<MapContract.View> {
        fun getPlaces()
        fun getUsers()
        fun setUserLocation(location: Location)
    }
}