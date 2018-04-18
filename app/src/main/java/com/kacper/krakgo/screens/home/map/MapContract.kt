package com.kacper.krakgo.screens.home.map

import android.location.Location
import com.kacper.krakgo.model.Place
import com.kacper.krakgo.model.UserDetails
import com.kacper.krakgo.mvp.MvpPresenter
import com.kacper.krakgo.mvp.MvpView
import com.kacper.krakgo.screens.home.profile.ProfileContract

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