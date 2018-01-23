package com.kacper.and_krakgo.screens.home

import android.os.Bundle
import android.support.design.internal.BottomNavigationItemView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity

import com.kacper.and_krakgo.R
import com.kacper.and_krakgo.helpers.FragmentHelper
import com.kacper.and_krakgo.screens.home.favourites.FavouritesFragment
import com.kacper.and_krakgo.screens.home.map.MapFragment
import com.kacper.and_krakgo.screens.home.messages.MessagesFragment
import com.kacper.and_krakgo.screens.home.profile.ProfileFragment
import com.kacper.and_krakgo.screens.home.recent.RecentFragment

import butterknife.ButterKnife
import kotlinx.android.synthetic.main.activity_home_main.*

/**
 * Created by kacper on 22/10/2017.
 */

class HomeMainActivity : AppCompatActivity() {
    private var mFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_main)
        initBottomBar()

    }

    private fun initBottomBar() {
        bottom_bar.enableItemShiftingMode(false)
        bottom_bar.enableShiftingMode(false)

        bottom_bar.setOnNavigationItemSelectedListener { tabId->
            mFragment = when(tabId.itemId){
                R.id.tab_chat -> MessagesFragment()
                R.id.tab_favourites -> FavouritesFragment()
                R.id.tab_recent -> RecentFragment()
                R.id.tab_profile -> ProfileFragment()
                R.id.tab_map -> MapFragment()
                else -> ProfileFragment()
            }
            FragmentHelper.changeFragments(supportFragmentManager, mFragment)
            true
        }
    }
}
