package com.kacper.and_krakgo.screens.home.profile

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.DecelerateInterpolator
import android.view.animation.RotateAnimation
import android.widget.ArrayAdapter
import android.widget.ProgressBar
import android.widget.TextView

import com.google.firebase.auth.FirebaseAuth
import com.kacper.and_krakgo.KrakGoApp
import com.kacper.and_krakgo.R
import com.kacper.and_krakgo.helpers.GlideHelper
import com.kacper.and_krakgo.helpers.SharedPreferencesHelper
import com.kacper.and_krakgo.helpers.ToastMessageHelper
import com.kacper.and_krakgo.screens.sign_in.SignInActivity

import kotlinx.android.synthetic.main.fragment_profile.*

/**
 * Created by kacper on 04/11/2017.
 */

class ProfileFragment : Fragment() {

    val mFirebaseUser = KrakGoApp.getCurrentUser()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_profile, container, false)
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setListener()
        if (mFirebaseUser != null) {
            tv_profile_email.text = mFirebaseUser.email
            tv_profile_display_name.text = mFirebaseUser.displayName
            GlideHelper.loadWithProgress(context, cv_profile_avatar, ProgressBar(context), mFirebaseUser.photoUrl)
        }
    }

    private fun setListener() {
        fl_logout_button.setOnClickListener({
            KrakGoApp.logout()
            val intent = Intent(context, SignInActivity::class.java)
            startActivity(intent)
            activity?.finish()
        })
        profile_expendable_main.setOnClickListener({
            var rotateAnimation: RotateAnimation = if (profile_expendable_layout.isExpanded)
                RotateAnimation(180.0f, 0.0f, Animation.RELATIVE_TO_SELF,
                        0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
            else
                RotateAnimation(0.0f, 180.0f, Animation.RELATIVE_TO_SELF,
                        0.5f, Animation.RELATIVE_TO_SELF, 0.5f)

            rotateAnimation.interpolator = DecelerateInterpolator()
            rotateAnimation.duration = 500
            rotateAnimation.fillAfter = true
            iv_profile_status_arrow.startAnimation(rotateAnimation)
            profile_expendable_layout.toggle()

        })

    }
}


