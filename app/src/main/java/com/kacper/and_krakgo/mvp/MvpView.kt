package com.kacper.and_krakgo.mvp


import android.content.Context
import java.lang.Exception

/**
 * Created by kacper on 21/01/2018.
 */
interface MvpView {

    fun showError(error: Exception)

    fun showMessage(messageId: Int)
}