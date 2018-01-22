package com.kacper.and_krakgo.screens.home.profile

import android.net.Uri
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.kacper.and_krakgo.helpers.FirebaseDatabaseHelper
import com.kacper.and_krakgo.model.UserDetails
import com.kacper.and_krakgo.mvp.MvpPresenterImpl

/**
 * Created by kacper on 21/01/2018.
 */
class ProfilePresenter : MvpPresenterImpl<ProfileContract.View>(),
        ProfileContract.Presenter{
    override fun updateUserAvatar(uri: Uri) {

    }

    override fun saveUserDetails(userDetails: UserDetails) {
        getDatabaseReference()
                .child(FirebaseDatabaseHelper.USER_DETAILS)
                .child(getCurrentUser().uid)
                .setValue(userDetails)
                .addOnCompleteListener({
                    mView?.userSaveComplete()
                })
                .addOnFailureListener({
                    mView?.onError(it)
                })
    }

    override fun getUserDetails(userId: String) {
        getDatabaseReference()
                .child(FirebaseDatabaseHelper.USER_DETAILS)
                .child(userId)
                .addListenerForSingleValueEvent(object : ValueEventListener{
                    override fun onCancelled(p0: DatabaseError?) {

                    }

                    override fun onDataChange(p0: DataSnapshot?) {
                        if(p0!!.exists()){
                            val userDetails = p0.getValue(UserDetails::class.java)
                            mView!!.showUserDetails(userDetails)
                        }
                    }

                })
    }

}