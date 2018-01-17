package com.kacper.and_krakgo.screens.sign_in.register;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by kacper on 22/10/2017.
 */

public class RegisterPresenter implements RegisterContract.Presenter {

    private FirebaseAuth mFirebaseAuth;
    private RegisterContract.View mView;

    RegisterPresenter(RegisterContract.View view){
        mView = view;
        mFirebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void signInWithEmail(String username, String email) {
        mFirebaseAuth.createUserWithEmailAndPassword(username, email)
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        mView.showError(e);
                    }
                })
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                       if(task.isSuccessful()){
                           mView.onSignUpCompleted();
                       } else {
                           mView.showError(task.getException());
                       }
                    }
                });
    }
}
