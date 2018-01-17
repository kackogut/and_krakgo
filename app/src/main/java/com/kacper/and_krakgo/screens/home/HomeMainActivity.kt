package com.kacper.and_krakgo.screens.home

import android.os.Bundle
import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.widget.FrameLayout

import com.kacper.and_krakgo.R
import com.kacper.and_krakgo.helpers.FragmentHelper
import com.kacper.and_krakgo.screens.home.favourites.FavouritesFragment
import com.kacper.and_krakgo.screens.home.map.MapFragment
import com.kacper.and_krakgo.screens.home.messages.MessagesFragment
import com.kacper.and_krakgo.screens.home.profile.ProfileFragment
import com.kacper.and_krakgo.screens.home.recent.RecentFragment
import com.roughike.bottombar.BottomBar
import com.roughike.bottombar.OnTabSelectListener

import butterknife.BindView
import butterknife.ButterKnife
import kotlinx.android.synthetic.main.fragment_home_main.*

/**
 * Created by kacper on 22/10/2017.
 */

class HomeMainActivity : AppCompatActivity() {
    private var mFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_home_main)
        ButterKnife.bind(this)
        initBottomBar()

    }

    private fun initBottomBar() {
        bottom_bar.setDefaultTab(R.id.tab_map)
        bottom_bar.setOnTabSelectListener { tabId ->
            when (tabId) {
                R.id.tab_chat -> mFragment = MessagesFragment()
                R.id.tab_favourites -> mFragment = FavouritesFragment()
                R.id.tab_recent -> mFragment = RecentFragment()
                R.id.tab_profile -> mFragment = ProfileFragment()
                R.id.tab_map -> mFragment = MapFragment()
                else -> mFragment = MapFragment()
            }
            FragmentHelper.changeFragments(supportFragmentManager, mFragment)
        }
    }
}
