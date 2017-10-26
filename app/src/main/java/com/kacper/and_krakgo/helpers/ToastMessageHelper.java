package com.kacper.and_krakgo.helpers;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by kacper on 22/10/2017.
 */

public class ToastMessageHelper {
    public static void showToast(Context context, String textToShow, boolean isLong) {
        if (isLong) {
            Toast.makeText(context, textToShow, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, textToShow, Toast.LENGTH_SHORT).show();
        }
    }
    public static void showShortToast(Context context, String textToShow){
        showToast(context, textToShow, false);
    }
    public static void showShortToast(Context context, int resourceId){
        showShortToast(context, context.getString(resourceId));
    }
}
