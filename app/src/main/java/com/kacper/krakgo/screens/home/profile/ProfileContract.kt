package com.kacper.krakgo.screens.home.profile

import android.net.Uri
import com.kacper.krakgo.model.UserDetails
import com.kacper.krakgo.mvp.MvpPresenter
import com.kacper.krakgo.mvp.MvpView

/**
 * Created by kacper on 21/01/2018.
 */
object ProfileContract {

    interface View : MvpView {
        fun showUserDetails(userDetails: UserDetails?)

        fun userSaveComplete()

        fun onError(error: Exception)

        fun photoUploadSuccess(uri: Uri)
    }

    interface Presenter : MvpPresenter<View> {
        fun saveUserDetails(userDetails: UserDetails)

        fun updateUserAvatar(uri:Uri)
    }
}