package com.kacper.and_krakgo.screens.home.map

import com.kacper.and_krakgo.model.Place
import com.kacper.and_krakgo.mvp.MvpPresenter
import com.kacper.and_krakgo.mvp.MvpView
import com.kacper.and_krakgo.screens.home.profile.ProfileContract

/**
 * Created by kacper on 10/03/2018.
 */
object MapContract {
    interface View : MvpView {
        fun setPlaces(places:ArrayList<Place>)
    }
    interface Presenter : MvpPresenter<MapContract.View> {
        fun getPlaces()
    }
}