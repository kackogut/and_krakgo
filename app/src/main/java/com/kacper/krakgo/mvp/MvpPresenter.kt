package com.kacper.krakgo.mvp

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.kacper.krakgo.model.UserDetails

/**
 * Created by kacper on 21/01/2018.
 */
interface MvpPresenter<in V : MvpView> {

    fun attachView(view: V)

    fun detachView()

    fun getCurrentUser() : FirebaseUser

    fun getDatabaseReference() : DatabaseReference

    fun getStorageReference() : StorageReference

    fun getUserDetails(userID: String, listener: ValueEventListener)
}