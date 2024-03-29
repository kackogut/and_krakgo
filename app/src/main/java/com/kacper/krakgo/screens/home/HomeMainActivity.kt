package com.kacper.krakgo.screens.home

import android.os.Bundle
import android.os.Handler
import android.os.PersistableBundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity

import com.kacper.krakgo.R
import com.kacper.krakgo.helpers.FragmentHelper
import com.kacper.krakgo.helpers.SnackbarHelper
import com.kacper.krakgo.helpers.ToastMessageHelper
import com.kacper.krakgo.screens.home.map.MapFragment
import com.kacper.krakgo.screens.home.messages.MessagesFragment
import com.kacper.krakgo.screens.home.profile.ProfileFragment
import com.kacper.krakgo.screens.home.forum.ForumFragment
import com.kacper.krakgo.screens.sign_in.login.LoginFragment

import kotlinx.android.synthetic.main.activity_home_main.*

/**
 * Created by kacper on 22/10/2017.
 */

class HomeMainActivity : AppCompatActivity() {

    private val CURRENT_FRAGMENT = "currentFragment"
    private var mFragment: HashMap<Int, Fragment> = HashMap()
    private var wasBackPressed = false
    private var mCurrentItem = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_main)
        initFragments()
        initBottomBar()
        if(savedInstanceState != null &&
                supportFragmentManager.getFragment(savedInstanceState, CURRENT_FRAGMENT)!= null){
            mCurrentItem = bottom_bar.currentItem
            mFragment[mCurrentItem] = supportFragmentManager.getFragment(
                    savedInstanceState, CURRENT_FRAGMENT)
            FragmentHelper.changeFragments(supportFragmentManager,
                    mFragment[mCurrentItem]!!)

        } else {
            bottom_bar.currentItem = 1
        }


    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        supportFragmentManager.putFragment(outState, CURRENT_FRAGMENT,
                mFragment[mCurrentItem])
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
            FragmentHelper.changeFragments(supportFragmentManager,
                    mFragment[tabId.itemId]!!)
            mCurrentItem = tabId.itemId
            true
        }

    }

    override fun onBackPressed() {
        if (wasBackPressed) {
            finish()
        } else {
            wasBackPressed = true
            SnackbarHelper.showSuccess(baseContext, R.string.press_back_again_to_exit, bottom_bar)
            val h = Handler()
            h.postDelayed({ wasBackPressed = false }, 2000)
        }
    }
}
