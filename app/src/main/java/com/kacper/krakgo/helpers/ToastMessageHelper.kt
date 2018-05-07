package com.kacper.krakgo.helpers

import android.content.Context
import android.widget.Toast

import com.kacper.krakgo.KrakGoApp

/**
 * Created by kacper on 22/10/2017.
 */

object ToastMessageHelper {
    private fun showToast(context: Context?, textToShow: String, isLong: Boolean) {
        if (isLong) {
            Toast.makeText(context, textToShow, Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(context, textToShow, Toast.LENGTH_SHORT).show()
        }
    }

    fun showShortToast(context: Context?, textToShow: String) {
        showToast(context, textToShow, false)
    }

    fun showShortToast(context: Context?, resourceId: Int) {
        if(context!= null) showShortToast(context, context.getString(resourceId))
    }
}
