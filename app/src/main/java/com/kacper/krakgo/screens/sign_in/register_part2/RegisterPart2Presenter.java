package com.kacper.krakgo.screens.sign_in.register_part2;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.kacper.krakgo.KrakGoApp;
import com.kacper.krakgo.helpers.FirebaseDatabaseHelper;
import com.kacper.krakgo.model.UserDetails;

/**
 * Created by kacper on 27/10/2017.
 */

public class RegisterPart2Presenter implements RegisterPart2Contract.Presenter {

    private RegisterPart2Contract.View mView;
    private Context mContext;

    public RegisterPart2Presenter(RegisterPart2Contract.View mView, Context mContext) {
        this.mView = mView;
        this.mContext = mContext;
    }

    @Override
    public void saveUserDetails(Uri mPhotoUri) {
        FirebaseUser user = KrakGoApp.getCurrentUser();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference reference = storageRef.child("avatars/" + user.getUid());

        UploadTask uploadTask = reference.putFile(mPhotoUri);
        uploadTask
                .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        mView.photoUploadFinished(task.getResult().getDownloadUrl());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        mView.showError(e);
                    }
                });
    }

    @Override
    public void updateUserProfile(UserDetails userDetails) {
        final FirebaseUser user = KrakGoApp.getCurrentUser();
        userDetails.setUserID(user.getUid());
        final UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder()
                .setDisplayName(userDetails.getDisplay_name())
                .setPhotoUri(Uri.parse(userDetails.getPhoto_url()))
                .build();

        DatabaseReference database = KrakGoApp.getFirebaseReference();
        database.child(FirebaseDatabaseHelper.USER_DETAILS)
                .child(user.getUid())
                .setValue(userDetails)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        user.updateProfile(profileChangeRequest).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                mView.userDetailsUpdated();
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        mView.showError(e);
                    }
                });
    }


}
