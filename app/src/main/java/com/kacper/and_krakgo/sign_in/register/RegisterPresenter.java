package com.kacper.and_krakgo.sign_in.register;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by kacper on 22/10/2017.
 */

public class RegisterPresenter implements RegisterContract.Presenter {

    private FirebaseAuth mFirebaseAuth;
    private RegisterContract.View mView;

    public RegisterPresenter(RegisterContract.View view){
        mView = view;
        mFirebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void signInWithEmail(String username, String email) {
        mFirebaseAuth.createUserWithEmailAndPassword(username, email)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                       if(task.isSuccessful()){
                           mView.onSignUpCompleted();
                       } else {
                           mView.showError(task.getException().toString());
                       }
                    }
                });
    }
}
