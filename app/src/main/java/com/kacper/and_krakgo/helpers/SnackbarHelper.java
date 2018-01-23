package com.kacper.and_krakgo.helpers;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.kacper.and_krakgo.KrakGoApp;
import com.kacper.and_krakgo.R;

/**
 * Created by kacper on 22/10/2017.
 */

public class SnackbarHelper {
    public static void showError(String text, View parentLayout){
        showSnackbar(text,parentLayout, R.color.mapStatusInvisible);

    }
    private static void showSnackbar(String text, View parentLayout, int color){
        Snackbar snackbar = Snackbar.make(parentLayout, text, Snackbar.LENGTH_LONG);
        View snackbarView = (View) snackbar.getView();
        snackbarView.setBackgroundColor(ContextCompat.getColor(KrakGoApp.getApplicationCtx(), color));
        snackbar.show();
    }
}
