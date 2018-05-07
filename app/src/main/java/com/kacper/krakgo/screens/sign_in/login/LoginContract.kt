package com.kacper.krakgo.screens.sign_in.login

import com.kacper.krakgo.mvp.MvpPresenter
import com.kacper.krakgo.mvp.MvpView

object LoginContract {
    interface View : MvpView{
        fun onLoginSuccesfull()
    }

    interface Presenter : MvpPresenter<View>{
        fun loginWithEmail(email: String, password: String)
    }
}