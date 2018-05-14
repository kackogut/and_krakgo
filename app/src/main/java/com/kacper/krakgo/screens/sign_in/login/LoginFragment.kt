package com.kacper.krakgo.screens.sign_in.login

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TextInputLayout
import android.support.v4.app.Fragment
import android.support.v7.widget.AppCompatCheckBox
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ProgressBar

import com.kacper.krakgo.model.enums.InputTypes
import com.kacper.krakgo.R
import com.kacper.krakgo.helpers.SharedPreferencesHelper
import com.kacper.krakgo.helpers.ToastMessageHelper
import com.kacper.krakgo.helpers.ValidationHelper
import com.kacper.krakgo.screens.home.HomeMainActivity
import com.kacper.krakgo.rxbus.SignInEvents
import com.kacper.krakgo.views.CustomTextWatcher

import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.kacper.krakgo.mvp.MvpFragment
import kotlinx.android.synthetic.main.fragment_login.*

/**
 * Created by kacper on 19/10/2017.
 */

class LoginFragment : MvpFragment<LoginContract.View, LoginContract.Presenter>(),
        LoginContract.View {

    override var mPresenter: LoginContract.Presenter = LoginPresenter()

    companion object {
        const val TAG = "LoginFragment"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setListeners()
    }

    override fun onLoginSuccesfull() {
        showProgress(false)
        SharedPreferencesHelper.saveToSharedPreferences(context,
                SharedPreferencesHelper.REMEMBER_USER, cb_remember_me.isChecked)
        ToastMessageHelper.showShortToast(context, R.string.login_succesfull)
        val intent = Intent(context, HomeMainActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }

    override fun showError(error: String) {
        showProgress(false)
        ToastMessageHelper.showShortToast(context, error)
    }

    private fun showProgress(showProgress: Boolean) {
        if (showProgress) {
            progress_bar_login?.visibility = View.VISIBLE
            login_button?.isEnabled = false
        } else {
            progress_bar_login?.visibility = View.GONE
            login_button?.isEnabled = true
        }
    }

    private fun setListeners() {
        login_email_input_layout?.editText?.addTextChangedListener(object : CustomTextWatcher() {
            override fun afterTextChanged(editable: Editable) {
                super.afterTextChanged(editable)
                showOrHideError(editable.toString(), InputTypes.EMAIL)
            }
        })
        login_password_input_layout?.editText?.addTextChangedListener(object : CustomTextWatcher() {
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                super.onTextChanged(charSequence, i, i1, i2)
                showOrHideError(charSequence.toString(), InputTypes.PASSWORD)
            }
        })

        login_button?.setOnClickListener({
            if (login_email_input_layout.editText!!.length() > 0 &&  login_password_input_layout.editText!!.length() > 0
                    && (!login_email_input_layout.isErrorEnabled || !login_password_input_layout.isErrorEnabled)) {
                showProgress(true)
                mPresenter.loginWithEmail(login_email_input_layout.editText?.text.toString(),
                        login_password_input_layout.editText?.text.toString())
            } else
                ToastMessageHelper.showShortToast(context,
                        getString(R.string.error_fields_empty_or_invalid))
        })

        forgot_password_text_view.setOnClickListener({
            SignInEvents.publish(SignInEvents.ACTION_FORGOT_PASSSWORD_CLICKED)
        })
    }

    private fun getInputLayoutByType(inputType: InputTypes): TextInputLayout? {
        return if (inputType == InputTypes.PASSWORD)
            login_password_input_layout
        else
            login_email_input_layout

    }

    fun showOrHideError(message: String, type: InputTypes) {
        val textInputLayout = getInputLayoutByType(type)
        val error = ValidationHelper.validateText(context, message, type)
        if (error == null) {
            textInputLayout?.isErrorEnabled = false
        } else {
            textInputLayout?.isEnabled = true
            textInputLayout?.error = error
        }
    }
}
