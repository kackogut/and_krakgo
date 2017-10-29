package com.kacper.and_krakgo.helpers;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.kacper.and_krakgo.R;

/**
 * Created by kacper on 22/10/2017.
 */

public class FragmentHelper {
    public static void addFragment(FragmentManager fragmentManager, Fragment fragment, String fragmentTag){
        fragmentManager.beginTransaction()
                .add(R.id.fragment_container, fragment, fragmentTag)
                .addToBackStack(fragmentTag)
                .commit();
    }
    public static void changeFragments(FragmentManager fragmentManager, Fragment fragment, String fragmentTag){
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment, fragmentTag)
                .setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left)
                .addToBackStack(fragmentTag)
                .commit();

    }
    public static void removeAndChangeFragment(FragmentManager fragmentManager, Fragment fragment){
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left)
                .commit();
    }
    public static Fragment getVisibleFragment(FragmentManager fragmentManager){
        return fragmentManager.findFragmentById(R.id.fragment_container);
    }
}
