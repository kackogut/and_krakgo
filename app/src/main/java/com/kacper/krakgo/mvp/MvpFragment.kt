package com.kacper.krakgo.mvp

import android.support.v4.app.Fragment
import android.os.Bundle
import android.view.View
import com.kacper.krakgo.R
import com.kacper.krakgo.helpers.SnackbarHelper
import java.lang.Exception

/**
 * Created by kacper on 21/01/2018.
 */
@Suppress("UNCHECKED_CAST")
abstract class MvpFragment<in V : MvpView, T : MvpPresenter<V>>
    : Fragment(), MvpView {

    protected abstract var mPresenter: T


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter.attachView(view = this as V)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }

    override fun showError(error: Exception?) {
        showError(error?.localizedMessage?:context!!.getString(R.string.error_default))
    }

    override fun showMessage(messageId: Int) {
        showMessage(context!!.getString(messageId))
    }

    override fun showMessage(message: String) {
    }
}