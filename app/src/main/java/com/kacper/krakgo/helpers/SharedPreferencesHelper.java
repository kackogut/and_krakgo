package com.kacper.krakgo.helpers;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.kacper.krakgo.KrakGoApp;

/**
 * Created by kacper on 23/10/2017.
 */

public class SharedPreferencesHelper {
    private static final String PREFERENCES_NAME = "KrakGoShared";
    public static final String REMEMBER_USER = "remember_user";

    public static void saveToSharedPreferences(String key, boolean value){
        if(KrakGoApp.getApplicationCtx() != null) {
            SharedPreferences sharedPref = KrakGoApp.getApplicationCtx()
                    .getSharedPreferences(PREFERENCES_NAME, 0);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putBoolean(key, value);
            editor.commit();
        }
    }
    public static Boolean getBoolean( String key, boolean defaultValue){
        SharedPreferences sharedPref = KrakGoApp.getApplicationCtx()
                .getSharedPreferences(PREFERENCES_NAME, 0);
        return sharedPref.getBoolean(key, defaultValue);
    }
    public static Boolean getBoolean(String key){
        return getBoolean(key, false);
    }
}
