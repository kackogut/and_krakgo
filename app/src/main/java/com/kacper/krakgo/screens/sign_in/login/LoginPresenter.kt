package com.kacper.krakgo.screens.sign_in.login

import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.kacper.krakgo.mvp.MvpPresenterImpl

/**
 * Created by kacper on 22/10/2017.
 */

class LoginPresenter : MvpPresenterImpl<LoginContract.View>(),
    LoginContract.Presenter{

    override fun loginWithEmail(email: String, password: String) {
        getAuthReference().signInWithEmailAndPassword(email, password)
                .addOnFailureListener { e -> mView?.showError(e) }
                .addOnCompleteListener { task ->
                    if (task.isSuccessful)
                        mView?.onLoginSuccesfull()
                    else
                        mView?.showError(task.exception!!)
                }
    }
}
