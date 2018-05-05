package com.kacper.krakgo.screens.sign_in.register_part2

import android.net.Uri
import com.kacper.krakgo.model.UserDetails
import com.kacper.krakgo.mvp.MvpPresenter
import com.kacper.krakgo.mvp.MvpView

object RegisterPart2Contract {
    interface Presenter : MvpPresenter<RegisterPart2Contract.View>{
        fun saveUserDetails(mPhotoUri: Uri)
        fun updateUserProfile(userDetails: UserDetails)
    }

    interface View : MvpView{
        fun photoUploadFinished(downloadUrl: Uri)
        fun userDetailsUpdated()
        override fun showError(e: Exception)
    }
}