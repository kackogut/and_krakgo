package com.kacper.and_krakgo.screens.home.profile

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar

import com.google.firebase.auth.FirebaseAuth
import com.kacper.and_krakgo.R
import com.kacper.and_krakgo.helpers.GlideHelper

import kotlinx.android.synthetic.main.fragment_profile.*

/**
 * Created by kacper on 04/11/2017.
 */

class ProfileFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_profile, container, false)
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            tv_profile_email.text = user.email
            tv_profile_display_name.text = user.displayName
            GlideHelper.loadWithProgress(context, cv_profile_avatar, ProgressBar(context), user.photoUrl)
        }
    }
}
