package com.kacper.krakgo.mvp

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.kacper.krakgo.KrakGoApp
import com.kacper.krakgo.helpers.FirebaseDatabaseHelper
import com.kacper.krakgo.model.UserDetails

/**
 * Created by kacper on 21/01/2018.
 */
open class MvpPresenterImpl<V : MvpView> : MvpPresenter<V> {
    override fun getStorageReference(): StorageReference {
        return FirebaseStorage.getInstance().reference
    }

    protected var mView: V? = null

    override fun attachView(view: V) {
        mView = view
    }

    override fun detachView() {
        mView = null
    }

    override fun getCurrentUser(): FirebaseUser {
        return FirebaseAuth.getInstance().currentUser!!
    }

    override fun getDatabaseReference(): DatabaseReference {
        return FirebaseDatabase.getInstance().reference
    }

    override fun getUserDetails(userID: String, listener: ValueEventListener) {
        getDatabaseReference()
                .child(FirebaseDatabaseHelper.USER_DETAILS)
                .child(userID)
                .addListenerForSingleValueEvent(listener)
    }
}