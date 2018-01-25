package com.kacper.and_krakgo.mvp

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.kacper.and_krakgo.KrakGoApp

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
        return KrakGoApp.getCurrentUser()
    }

    override fun getDatabaseReference(): DatabaseReference {
        return KrakGoApp.getFirebaseReference()
    }
}