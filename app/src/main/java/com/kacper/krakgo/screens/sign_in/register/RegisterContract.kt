package com.kacper.krakgo.screens.sign_in.register

import com.kacper.krakgo.mvp.MvpPresenter
import com.kacper.krakgo.mvp.MvpView

object RegisterContract {
    interface Presenter : MvpPresenter<View> {
        fun signInWithEmail(username: String, email: String)
    }

    interface View : MvpView {
        fun onSignUpCompleted()
    }
}