package com.kacper.and_krakgo.mvp

import android.support.v4.app.Fragment
import android.os.Bundle
import com.kacper.and_krakgo.helpers.SnackbarHelper
import java.lang.Exception

/**
 * Created by kacper on 21/01/2018.
 */
@Suppress("UNCHECKED_CAST")
abstract class MvpFragment<in V : MvpView, T : MvpPresenter<V>> : Fragment(), MvpView {

    protected abstract var mPresenter: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter.attachView(view = this as V)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }

    override fun showError(error: Exception) {
    }

    override fun showMessage(messageId: Int) {
    }

}