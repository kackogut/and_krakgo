package com.kacper.krakgo.screens.sign_in.register

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.TextInputLayout
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.kacper.krakgo.model.enums.InputTypes
import com.kacper.krakgo.R
import com.kacper.krakgo.helpers.FragmentHelper
import com.kacper.krakgo.helpers.ToastMessageHelper
import com.kacper.krakgo.helpers.ValidationHelper
import com.kacper.krakgo.rxbus.SignInEvents
import com.kacper.krakgo.screens.sign_in.register_part2.RegisterPart2Fragment
import com.kacper.krakgo.views.CustomTextWatcher

import butterknife.ButterKnife
import butterknife.OnClick
import com.kacper.krakgo.mvp.MvpFragment
import kotlinx.android.synthetic.main.fragment_register_1.*

/**
 * Created by kacper on 22/10/2017.
 */

class RegisterFragment : MvpFragment<RegisterContract.View, RegisterContract.Presenter>(),
        RegisterContract.View {

    override var mPresenter: RegisterContract.Presenter = RegisterPresenter()
    private var isBottomButtonEnabled = true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_register_1, container, false)
        ButterKnife.bind(this, rootView)

        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setListeners()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SignInEvents.subscribe { integer ->
            if (isVisible && integer == SignInEvents.ACTION_BOTTOM_BUTTON_REGISTER_1)
                goToNextPage()
        }
    }

    private fun goToNextPage() {
        if (!(register_email_input.editText!!.length() > 0
                        && register_password_input.editText!!.length() > 0
                        && register_retype_password_input.editText!!.length() > 0) ||
                register_email_input.isErrorEnabled || register_password_input.isErrorEnabled
                || register_retype_password_input.isErrorEnabled) {
            ToastMessageHelper.showShortToast(context!!, R.string.error_fields_empty_or_invalid)
        } else if (!terms_accepted_checkbox.isChecked) {
            ToastMessageHelper.showShortToast(context!!, R.string.you_have_to_accept_terms)
        } else if (isBottomButtonEnabled) {
            isBottomButtonEnabled = false
            showProgress()
            mPresenter.signInWithEmail(register_email_input.editText!!.text.toString(),
                    register_password_input.editText!!.text.toString())
        }
    }

    private fun showProgress() {
        register_email_input.isEnabled = isBottomButtonEnabled
        register_password_input.isEnabled = isBottomButtonEnabled
        register_retype_password_input.isEnabled = isBottomButtonEnabled
        terms_accepted_checkbox.isEnabled = isBottomButtonEnabled
        if (isBottomButtonEnabled)
            progress_bar.visibility = View.GONE
        else
            progress_bar.visibility = View.VISIBLE
    }

    override fun onSignUpCompleted() {
        FragmentHelper.removeAndChangeFragment(fragmentManager!!, RegisterPart2Fragment())
    }

    override fun showMessage(messageId: Int) {

    }

    override fun showError(error: String) {
        ToastMessageHelper.showShortToast(context, error)
        isBottomButtonEnabled = true
        showProgress()
    }

    private fun setListeners() {
        register_email_input.editText!!.addTextChangedListener(object : CustomTextWatcher() {
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                super.onTextChanged(charSequence, i, i1, i2)
                showOrHideError(charSequence.toString(), InputTypes.EMAIL)
            }
        })
        register_password_input.editText!!.addTextChangedListener(object : CustomTextWatcher() {
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                super.onTextChanged(charSequence, i, i1, i2)
                showOrHideError(charSequence.toString(), InputTypes.PASSWORD)
            }
        })
        register_retype_password_input.editText!!.addTextChangedListener(object : CustomTextWatcher() {
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                super.onTextChanged(charSequence, i, i1, i2)
                showOrHideError(charSequence.toString(), InputTypes.RETYPEPASSWORD)
                if (register_retype_password_input.isErrorEnabled) {
                    if (register_password_input.editText!!.text.toString()
                            != charSequence.toString()) {
                        register_retype_password_input.error = getString(R.string.password_dont_match)
                        register_retype_password_input.isErrorEnabled = true
                    } else
                        register_retype_password_input.isErrorEnabled = false
                }
            }
        })
        terms_textview.setOnClickListener({
            val browserIntent = Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://www.kacperkogut.com/pubgo/terms"))
            startActivity(browserIntent)
        })
    }

    private fun getInputLayoutByType(inputType: InputTypes): TextInputLayout? {
        return when (inputType) {
            InputTypes.PASSWORD -> register_password_input
            InputTypes.RETYPEPASSWORD -> register_retype_password_input
            InputTypes.EMAIL -> register_email_input
            else -> register_email_input
        }
    }

    fun showOrHideError(message: String, type: InputTypes) {
        val textInputLayout = getInputLayoutByType(type)
        val error = ValidationHelper.validateText(context!!, message, type)
        if (error == null) {
            textInputLayout!!.isErrorEnabled = false
        } else {
            textInputLayout!!.isEnabled = true
            textInputLayout.error = error
        }
    }

}
