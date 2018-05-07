package com.kacper.krakgo.screens.sign_in.register_part2

import android.content.Context
import android.net.Uri

import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.storage.FirebaseStorage
import com.kacper.krakgo.KrakGoApp
import com.kacper.krakgo.helpers.FirebaseDatabaseHelper
import com.kacper.krakgo.model.UserDetails
import com.kacper.krakgo.mvp.MvpPresenterImpl
import com.kacper.krakgo.screens.home.map.MapContract

/**
 * Created by kacper on 27/10/2017.
 */

class RegisterPart2Presenter : MvpPresenterImpl<RegisterPart2Contract.View>(),
            RegisterPart2Contract.Presenter {

    override fun saveUserDetails(mPhotoUri: Uri) {

        val storage = FirebaseStorage.getInstance()
        val storageRef = storage.reference
        val reference = storageRef.child("avatars/" + getCurrentUser()?.uid)

        val uploadTask = reference.putFile(mPhotoUri)
        uploadTask
                .addOnCompleteListener { task -> mView?.photoUploadFinished(task.result.downloadUrl!!) }
                .addOnFailureListener { e -> mView?.showError(e) }
    }

    override fun updateUserProfile(userDetails: UserDetails) {
        userDetails.userID = getCurrentUser()?.uid
        val profileChangeRequest = UserProfileChangeRequest.Builder()
                .setDisplayName(userDetails.display_name)
                .setPhotoUri(Uri.parse(userDetails.photo_url))
                .build()

        getDatabaseReference().child(FirebaseDatabaseHelper.USER_DETAILS)
                .child(getCurrentUser()?.uid)
                .setValue(userDetails)
                .addOnCompleteListener { getCurrentUser()?.updateProfile(profileChangeRequest)
                        ?.addOnCompleteListener({ mView?.userDetailsUpdated() }) }
                .addOnFailureListener { e -> mView?.showError(e) }
    }


}
