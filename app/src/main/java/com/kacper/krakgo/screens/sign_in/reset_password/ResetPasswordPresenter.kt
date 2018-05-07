package com.kacper.krakgo.screens.sign_in.reset_password

import com.google.firebase.auth.FirebaseAuth
import com.kacper.krakgo.mvp.MvpPresenterImpl

/**
 * Created by kacper on 05/11/2017.
 */

class ResetPasswordPresenter :MvpPresenterImpl<ResetPasswordContract.View>(),
        ResetPasswordContract.Presenter {

    override fun sendResetPassword(email: String) {
        FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                .addOnCompleteListener { mView?.onResetSuccessful() }
                .addOnFailureListener { e -> mView?.showError(e.toString()) }
    }
}
