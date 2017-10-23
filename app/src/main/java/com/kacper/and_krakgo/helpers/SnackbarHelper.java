package com.kacper.and_krakgo.helpers;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * Created by kacper on 22/10/2017.
 */

public class SnackbarHelper {
    public static void showSnackbar(String text, View parentLayout, boolean isLong){

            Snackbar.make(parentLayout, text, Snackbar.LENGTH_LONG).show();

    }
}
