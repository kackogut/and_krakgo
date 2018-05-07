package com.kacper.krakgo.screens.sign_in.register

import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.kacper.krakgo.mvp.MvpPresenterImpl

/**
 * Created by kacper on 22/10/2017.
 */

class RegisterPresenter : MvpPresenterImpl<RegisterContract.View>(),
        RegisterContract.Presenter {

    override fun signInWithEmail(username: String, email: String) {
        getAuthReference().createUserWithEmailAndPassword(username, email)
                .addOnFailureListener { e -> mView?.showError(e) }
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        mView?.onSignUpCompleted()
                    } else {
                        mView?.showError(task.exception)
                    }
                }
    }
}
