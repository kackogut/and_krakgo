package com.kacper.krakgo.screens.sign_in.reset_password

import android.os.Bundle
import android.support.design.widget.TextInputLayout
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar

import com.kacper.krakgo.model.enums.InputTypes
import com.kacper.krakgo.R
import com.kacper.krakgo.helpers.ToastMessageHelper
import com.kacper.krakgo.helpers.ValidationHelper
import com.kacper.krakgo.rxbus.SignInEvents
import com.kacper.krakgo.views.CustomTextWatcher

import butterknife.BindView
import butterknife.ButterKnife
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.fragment_reset_password.*

/**
 * Created by kacper on 05/11/2017.
 */

class ResetPasswordFragment : Fragment(), ResetPasswordContract.View {


    private var mPresenter: ResetPasswordPresenter? = null

    private var isLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter = ResetPasswordPresenter(this)
        SignInEvents.subscribe { o ->
            if (isVisible && o == SignInEvents.ACTION_BOTTOM_BUTTON_PASSWORD) {
                isLoading = true
                showLoading()
                resetPassword()
            }
        }
    }

    private fun resetPassword() {
        if (reset_email_input.editText != null && reset_email_input.isErrorEnabled) {
            ToastMessageHelper.showShortToast(context!!, R.string.error_email_field)
        } else {
            mPresenter!!.sendResetPassword(reset_email_input.editText!!.text.toString())
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_reset_password, container, false)
        ButterKnife.bind(this, rootView)
        setListeners()
        return rootView
    }

    private fun setListeners() {
        reset_email_input.editText!!.addTextChangedListener(object : CustomTextWatcher() {
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                super.onTextChanged(charSequence, i, i1, i2)
                val error = ValidationHelper.validateText(context, charSequence.toString(), InputTypes.EMAIL)
                if (error == null) {
                    reset_email_input.isErrorEnabled = false
                } else {
                    reset_email_input.isEnabled = true
                    reset_email_input.error = error
                }
            }
        })
    }

    override fun showError(message: String) {
        ToastMessageHelper.showShortToast(context!!, message)
        isLoading = false
        showLoading()
    }

    override fun onResetSuccesful() {
        ToastMessageHelper.showShortToast(context!!, R.string.password_change_success)
        activity!!.onBackPressed()
    }

    private fun showLoading() {
        if (isLoading) {
            progress_bar.visibility = View.VISIBLE
        } else {
            progress_bar.visibility = View.GONE
        }
        reset_email_input.isEnabled = !isLoading
    }

    companion object {
        val TAG = "ResetPasswordFragment"
    }


}
