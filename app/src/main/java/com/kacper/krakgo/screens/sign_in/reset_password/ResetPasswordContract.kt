package com.kacper.krakgo.screens.sign_in.reset_password

import com.kacper.krakgo.mvp.MvpPresenter
import com.kacper.krakgo.mvp.MvpView

object ResetPasswordContract {

    interface View : MvpView {
        fun onResetSuccessful()
    }

    interface Presenter : MvpPresenter<View> {
        fun sendResetPassword(email: String)
    }
}