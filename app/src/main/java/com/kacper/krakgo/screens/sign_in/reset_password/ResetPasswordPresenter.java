package com.kacper.krakgo.screens.sign_in.reset_password;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by kacper on 05/11/2017.
 */

public class ResetPasswordPresenter implements ResetPasswordContract.Presenter{
    private ResetPasswordContract.View mView;

    public ResetPasswordPresenter(ResetPasswordContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void sendResetPassword(String email) {
        FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        mView.onResetSuccesful();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        mView.showError(e.toString());
                    }
                });
    }
}
