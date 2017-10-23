package com.kacper.and_krakgo.sign_in.login;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by kacper on 22/10/2017.
 */

public class LoginPresenter implements LoginContract.Presenter{
    private FirebaseAuth mFirebaseAuth;
    private LoginContract.View mView;

    public LoginPresenter(LoginContract.View view){
        mView = view;
        mFirebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void loginWithEmail(String email, String password) {
        mFirebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                            mView.onLoginSuccesfull();
                        else
                            mView.showError(task.getException());
                    }
                });
    }
}
