package com.kacper.krakgo.screens.home.profile

import android.net.Uri
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import com.kacper.krakgo.KrakGoApp
import com.kacper.krakgo.helpers.FirebaseDatabaseHelper
import com.kacper.krakgo.model.UserDetails
import com.kacper.krakgo.mvp.MvpPresenterImpl

/**
 * Created by kacper on 21/01/2018.
 */
class ProfilePresenter : MvpPresenterImpl<ProfileContract.View>(),
        ProfileContract.Presenter{
    override fun updateUserAvatar(uri: Uri) {
        val reference = getStorageReference().child("avatars/" + getCurrentUser()?.uid)
        reference.delete()
        val uploadTask = reference.putFile(uri)
        uploadTask
                .addOnCompleteListener { task -> mView?.photoUploadSuccess(
                        task.result.downloadUrl!!)}
                .addOnFailureListener { e -> mView?.showError(e) }
    }


    override fun saveUserDetails(userDetails: UserDetails) {
        getDatabaseReference()
                .child(FirebaseDatabaseHelper.USER_DETAILS)
                .child(getCurrentUser()?.uid)
                .setValue(userDetails)
                .addOnCompleteListener({
                    mView?.userSaveComplete()
                })
                .addOnFailureListener({
                    mView?.onError(it)
                })
    }

}