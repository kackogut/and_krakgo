package com.kacper.and_krakgo.mvp

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference

/**
 * Created by kacper on 21/01/2018.
 */
interface MvpPresenter<in V : MvpView> {

    fun attachView(view: V)

    fun detachView()

    fun getCurrentUser() : FirebaseUser

    fun getDatabaseReference() : DatabaseReference
}