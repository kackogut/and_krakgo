package com.kacper.and_krakgo.screens.home.map

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.kacper.and_krakgo.helpers.FirebaseDatabaseHelper
import com.kacper.and_krakgo.model.Place
import com.kacper.and_krakgo.mvp.MvpPresenterImpl
import com.kacper.and_krakgo.screens.home.forum.ForumPresenter

/**
 * Created by kacper on 10/03/2018.
 */
class MapPresenter : MvpPresenterImpl<MapContract.View>(),
        MapContract.Presenter {
    override fun getPlaces() {
            getDatabaseReference()
                    .child(FirebaseDatabaseHelper.PLACES)
                    .addValueEventListener(object : ValueEventListener {
                        override fun onCancelled(p0: DatabaseError?) {
                            Log.w(MapFragment::class.java.simpleName,
                                    "loadMessages:onCancelled", p0?.toException());
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