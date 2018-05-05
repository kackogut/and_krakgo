package com.kacper.krakgo.helpers

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager

import com.kacper.krakgo.R

/**
 * Created by kacper on 22/10/2017.
 */

object FragmentHelper {
    fun addFragment(fragmentManager: FragmentManager, fragment: Fragment, fragmentTag: String) {
        fragmentManager.beginTransaction()
                .add(R.id.fragment_container, fragment, fragmentTag)
                .commit()
    }

    @JvmOverloads
    fun changeFragments(fragmentManager: FragmentManager, fragment: Fragment, fragmentTag: String = fragment.javaClass.simpleName) {
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment, fragmentTag)
                .setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left)
                .addToBackStack(fragmentTag)
                .commit()

    }

    fun removeAndChangeFragment(fragmentManager: FragmentManager, fragment: Fragment) {
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left)
                .commit()
    }

    fun getVisibleFragment(fragmentManager: FragmentManager): Fragment {
        return fragmentManager.findFragmentById(R.id.fragment_container)
    }
}
