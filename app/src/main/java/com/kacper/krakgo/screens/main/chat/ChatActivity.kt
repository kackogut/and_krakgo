package com.kacper.krakgo.screens.main.chat

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.kacper.krakgo.R
import com.kacper.krakgo.helpers.FragmentHelper
import com.kacper.krakgo.helpers.GlideHelper
import com.kacper.krakgo.model.UserDetails
import kotlinx.android.synthetic.main.activity_chat.*

/**
 * Created by kacper on 30/01/2018.
 */
class ChatActivity : AppCompatActivity(){

    lateinit var mUserDetails: UserDetails
    lateinit var mFragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        mUserDetails = intent.getParcelableExtra(USER_DETAILS_EXTRA)
                ?: throw IllegalStateException("field $USER_DETAILS_EXTRA missing in Intent")

        mFragment = ChatWithIDFragment.newFragment(mUserDetails)
        FragmentHelper.addFragment(supportFragmentManager, mFragment, mFragment.tag)

        GlideHelper.load(baseContext, civ_chat_avatar, mUserDetails.photo_url)
        tv_chat_username.text = mUserDetails.display_name

        (mFragment as ChatWithIDFragment).getChatMessages()
    }

    companion object {
        private val USER_DETAILS_EXTRA = "user_details"

        fun newIntent(context: Context, userDetails: UserDetails): Intent {
            val intent = Intent(context, ChatActivity::class.java)
            intent.putExtra(USER_DETAILS_EXTRA, userDetails)
            return intent
        }
    }
}