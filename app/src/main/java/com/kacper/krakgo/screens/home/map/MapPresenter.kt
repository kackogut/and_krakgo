package com.kacper.krakgo.screens.home.map

import android.location.Location
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.kacper.krakgo.KrakGoApp
import com.kacper.krakgo.helpers.FirebaseDatabaseHelper
import com.kacper.krakgo.model.Place
import com.kacper.krakgo.model.UserDetails
import com.kacper.krakgo.mvp.MvpPresenterImpl
import com.kacper.krakgo.screens.home.forum.ForumPresenter

/**
 * Created by kacper on 10/03/2018.
 */
class MapPresenter : MvpPresenterImpl<MapContract.View>(),
        MapContract.Presenter {
    override fun setUserLocation(location: Location) {
        getDatabaseReference()
                .child(FirebaseDatabaseHelper.USER_DETAILS)
                .child(getCurrentUser().uid)
                .child("latitude")
                .setValue(location.latitude)

        getDatabaseReference()
                .child(FirebaseDatabaseHelper.USER_DETAILS)
                .child(getCurrentUser().uid)
                .child("longitude")
                .setValue(location.longitude)
    }

    override fun getUsers() {
        getDatabaseReference()
                .child(FirebaseDatabaseHelper.USER_DETAILS)
                .addValueEventListener(object : ValueEventListener{
                    override fun onCancelled(p0: DatabaseError?) {
                        Log.w(MapFragment::class.java.simpleName,
                                "loadUsers:onCancelled", p0?.toException());
                    }

                    override fun onDataChange(p0: DataSnapshot?) {
                        val values: ArrayList<UserDetails> = ArrayList()
                        if(p0!!.exists()){
                            p0.children.mapTo(values){
                                it.getValue(UserDetails::class.java)!!
                            }
                        }
                        mView?.setUsers(values)
                    }

                })
    }

    override fun getPlaces() {
            getDatabaseReference()
                    .child(FirebaseDatabaseHelper.PLACES)
                    .addValueEventListener(object : ValueEventListener {
                        override fun onCancelled(p0: DatabaseError?) {
                            Log.w(MapFragment::class.java.simpleName,
                                    "loadPlaces:onCancelled", p0?.toException());
                        }

                        override fun onDataChange(p0: DataSnapshot?) {
                            val values: ArrayList<Place> = ArrayList()
                            if (p0!!.exists()) {
                                p0.children.mapTo(values) {
                                    it.getValue(Place::class.java)!!
                                }

                            }
                            mView?.setPlaces(values)
                        }
                    })
    }

}