package com.kacper.krakgo.screens.dialogs

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatDialog
import android.view.View
import android.view.Window
import android.widget.ProgressBar
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.kacper.krakgo.KrakGoApp
import com.kacper.krakgo.R
import com.kacper.krakgo.helpers.DateHelper
import com.kacper.krakgo.helpers.FirebaseDatabaseHelper
import com.kacper.krakgo.helpers.GlideHelper
import com.kacper.krakgo.model.UserDetails
import com.kacper.krakgo.model.enums.MapVisibility
import com.kacper.krakgo.screens.main.chat.ChatActivity
import kotlinx.android.synthetic.main.dialog_user_info.*
import kotlinx.android.synthetic.main.item_user_basic_info.*
import java.util.*

/**
 * Created by kacper on 29/01/2018.
 */
class DialogUserInfo(var mActivty:Activity, var mUserDetails:UserDetails)
    : AppCompatDialog(mActivty) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_user_info)
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        getUserInfo()
        setListener()
    }

    private fun setListener() {
        fl_send_message_button.setOnClickListener({
            mActivty.startActivity(ChatActivity.newIntent(mActivty, mUserDetails))
            dismiss()
        })
    }

    private fun getUserInfo() {
        tv_profile_age.text = context.resources.getQuantityString(R.plurals.age_plural,
                DateHelper.getYearDifference(Date(mUserDetails.dob_time)),
                DateHelper.getYearDifference(Date(mUserDetails.dob_time)))
        tv_profile_email.visibility = View.GONE
        tv_profile_display_name.text = mUserDetails.display_name
        GlideHelper.loadWithProgress(context, cv_profile_avatar, ProgressBar(context), Uri.parse(mUserDetails.photo_url))
        tv_dialog_map_status_label.text = context.getString(
                MapVisibility.values()[mUserDetails.map_visibility.toInt()].stringResource)
        iv_dialog_map_status_circle.setColorFilter(ContextCompat.getColor(context!!,
                MapVisibility.values()[mUserDetails.map_visibility.toInt()].colourResource))
        if(mUserDetails.about_me != null)
            tv_dialog_about_me.text = mUserDetails.about_me
        else
            tv_dialog_about_me.visibility = View.GONE
    }
}