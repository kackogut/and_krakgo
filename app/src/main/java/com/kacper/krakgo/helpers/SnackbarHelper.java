package com.kacper.krakgo.helpers;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.kacper.krakgo.KrakGoApp;
import com.kacper.krakgo.R;

/**
 * Created by kacper on 22/10/2017.
 */

public class SnackbarHelper {
    public static void showError(String text, View parentLayout){
        showSnackbar(text,parentLayout, R.color.mapStatusInvisible);
    }
    public static void showSuccess(String text, View parentLayout){
        showSnackbar(text, parentLayout, R.color.mapStatusVisible);
    }
    public static void showSuccess(int textResource, View parentLayout){
        showSuccess(KrakGoApp.getApplicationCtx().getString(textResource), parentLayout);
    }
    public static void showError(int textResource, View parentLayout){
        showError(KrakGoApp.getApplicationCtx().getString(textResource), parentLayout);
    }
    private static void showSnackbar(String text, View parentLayout, int color){
        Snackbar snackbar = Snackbar.make(parentLayout, text, Snackbar.LENGTH_LONG);
        View snackbarView = (View) snackbar.getView();
        snackbarView.setBackgroundColor(ContextCompat.getColor(KrakGoApp.getApplicationCtx(), color));
        snackbar.show();
    }
}
