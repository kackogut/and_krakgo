package com.kacper.and_krakgo.screens.home.profile

import android.net.Uri
import com.kacper.and_krakgo.model.UserDetails
import com.kacper.and_krakgo.mvp.MvpPresenter
import com.kacper.and_krakgo.mvp.MvpView

/**
 * Created by kacper on 21/01/2018.
 */
object ProfileContract {

    interface View : MvpView {
        fun showUserDetails(userDetails: UserDetails?)

        fun userSaveComplete()

        fun onError(error: Exception)

        fun photoUploadSuccess()
    }

    interface Presenter : MvpPresenter<View> {
        fun getUserDetails(userId: String)

        fun saveUserDetails(userDetails: UserDetails)

        fun updateUserAvatar(uri:Uri)
    }
}