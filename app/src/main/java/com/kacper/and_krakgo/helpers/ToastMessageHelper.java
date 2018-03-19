package com.kacper.and_krakgo.helpers;

import android.content.Context;
import android.widget.Toast;

import com.kacper.and_krakgo.KrakGoApp;

/**
 * Created by kacper on 22/10/2017.
 */

public class ToastMessageHelper {
    public static void showToast(String textToShow, boolean isLong) {
        if (isLong) {
            Toast.makeText(KrakGoApp.getApplicationCtx(), textToShow, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(KrakGoApp.getApplicationCtx(), textToShow, Toast.LENGTH_SHORT).show();
        }
    }

    public static void showShortToast(String textToShow) {
        showToast(textToShow, false);
    }

    public static void showShortToast(int resourceId) {
        if (KrakGoApp.getApplicationCtx() != null)
            showShortToast(KrakGoApp.getApplicationCtx().getString(resourceId));
    }
}
