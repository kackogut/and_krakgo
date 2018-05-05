package com.kacper.krakgo.helpers

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences

import com.kacper.krakgo.KrakGoApp

/**
 * Created by kacper on 23/10/2017.
 */

object SharedPreferencesHelper {
    private val PREFERENCES_NAME = "KrakGoShared"
    val REMEMBER_USER = "remember_user"

    fun saveToSharedPreferences(context: Context, key: String, value: Boolean) {

        val sharedPref = context.getSharedPreferences(PREFERENCES_NAME, 0)
        val editor = sharedPref.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun getBoolean(context: Context, key: String, defaultValue: Boolean): Boolean {
        val sharedPref = context.getSharedPreferences(PREFERENCES_NAME, 0)
        return sharedPref.getBoolean(key, defaultValue)
    }

    fun getBoolean(key: String, context: Context): Boolean {
        return getBoolean(context, key, false)
    }
}
