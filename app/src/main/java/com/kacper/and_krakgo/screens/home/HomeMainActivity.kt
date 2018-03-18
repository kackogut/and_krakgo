package com.kacper.and_krakgo.screens.home

import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity

import com.kacper.and_krakgo.R
import com.kacper.and_krakgo.helpers.FragmentHelper
import com.kacper.and_krakgo.helpers.SnackbarHelper
import com.kacper.and_krakgo.helpers.ToastMessageHelper
import com.kacper.and_krakgo.screens.home.map.MapFragment
import com.kacper.and_krakgo.screens.home.messages.MessagesFragment
import com.kacper.and_krakgo.screens.home.profile.ProfileFragment
import com.kacper.and_krakgo.screens.home.forum.ForumFragment
import com.kacper.and_krakgo.screens.sign_in.login.LoginFragment

import kotlinx.android.synthetic.main.activity_home_main.*

/**
 * Created by kacper on 22/10/2017.
 */

class HomeMainActivity : AppCompatActivity() {
    private var mFragment: HashMap<Int, Fragment> = HashMap()
    private var wasBackPressed = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_main)
        initFragments()
        initBottomBar()

    }

    private fun initFragments() {
        mFragment[R.id.tab_forum] = ForumFragment()
        mFragment[R.id.tab_map] = MapFragment()
        mFragment[R.id.tab_messages] = MessagesFragment()
        mFragment[R.id.tab_profile] = ProfileFragment()
    }

    private fun initBottomBar() {
        bottom_bar.enableItemShiftingMode(false)
        bottom_bar.enableShiftingMode(false)

        bottom_bar.setOnNavigationItemSelectedListener { tabId ->
            FragmentHelper.changeFragments(supportFragmentManager, mFragment[tabId.itemId])
            true
        }
        bottom_bar.currentItem = 1
    }

    override fun onBackPressed() {

        if (wasBackPressed) {
            finish()
        } else {
            wasBackPressed = true
            SnackbarHelper.showSuccess(R.string.press_back_again_to_exit, bottom_bar)
            val h = Handler()
            h.postDelayed({ wasBackPressed = false }, 2000)
        }

    }
}
