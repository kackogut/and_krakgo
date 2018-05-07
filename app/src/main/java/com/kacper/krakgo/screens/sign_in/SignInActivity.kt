package com.kacper.krakgo.screens.sign_in

import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.View

import com.kacper.krakgo.R
import com.kacper.krakgo.helpers.FragmentHelper
import com.kacper.krakgo.helpers.ToastMessageHelper
import com.kacper.krakgo.rxbus.SignInEvents
import com.kacper.krakgo.screens.sign_in.register.RegisterFragment
import com.kacper.krakgo.screens.sign_in.login.LoginFragment
import com.kacper.krakgo.screens.sign_in.register_part2.RegisterPart2Fragment
import com.kacper.krakgo.screens.sign_in.reset_password.ResetPasswordFragment

import kotlinx.android.synthetic.main.activity_sign_in.*

/**
 * Created by kacper on 19/10/2017.
 */

class SignInActivity : AppCompatActivity() {

    private var mFragment: Fragment? = null
    private var wasBackPressed = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        sign_toolbar.visibility = View.INVISIBLE
        mFragment = LoginFragment()
        FragmentHelper.addFragment(supportFragmentManager, mFragment!!, LoginFragment.TAG)
        verifyView()
        setListeners()

        SignInEvents.subscribe { integer ->
            if (integer == SignInEvents.ACTION_FORGOT_PASSSWORD_CLICKED) {
                mFragment = ResetPasswordFragment()
                FragmentHelper.changeFragments(supportFragmentManager, mFragment!!, ResetPasswordFragment.TAG)
                verifyView()
            }
        }
    }

    override fun onBackPressed() {
        if (mFragment is LoginFragment) {
            if (wasBackPressed) {
                finish()
            } else {
                wasBackPressed = true
                ToastMessageHelper.showShortToast(baseContext, R.string.press_back_again_to_exit)
                val h = Handler()
                h.postDelayed({ wasBackPressed = false }, 2000)
            }
        } else {
            super.onBackPressed()
            mFragment = FragmentHelper.getVisibleFragment(supportFragmentManager)
            verifyView()
        }
    }

    private fun verifyView() {
        if (mFragment is LoginFragment) {
            sign_button.setText(R.string.dont_have_account_sign_label)
            sign_toolbar.visibility = View.GONE
        } else if (mFragment is RegisterFragment) {
            sign_button.setText(R.string.next_label)
            sign_toolbar.visibility = View.VISIBLE
        } else if (mFragment is ResetPasswordFragment) {
            sign_button.setText(R.string.reset_password_button)
            sign_toolbar.visibility = View.VISIBLE
        }
    }

    private fun setListeners(){
        arrow_back.setOnClickListener({
            onBackPressed()
        })

        sign_button.setOnClickListener({
            mFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
            when (mFragment) {
                is LoginFragment -> {
                    mFragment = RegisterFragment()
                    FragmentHelper.changeFragments(supportFragmentManager, mFragment!!,
                            RegisterFragment::class.java.simpleName)
                }
                is RegisterFragment -> SignInEvents.publish(SignInEvents.ACTION_BOTTOM_BUTTON_REGISTER_1)
                is RegisterPart2Fragment -> SignInEvents.publish(SignInEvents.ACTION_BOTTOM_BUTTON_REGISTER_2)
                is ResetPasswordFragment -> SignInEvents.publish(SignInEvents.ACTION_BOTTOM_BUTTON_PASSWORD)
            }
            verifyView()
        })
    }
}
