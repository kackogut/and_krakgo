package com.kacper.krakgo.helpers

import android.content.Context
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.view.View

import com.kacper.krakgo.KrakGoApp
import com.kacper.krakgo.R

/**
 * Created by kacper on 22/10/2017.
 */

object SnackbarHelper {
    fun showError(context: Context?, text: String?, parentLayout: View) {
        if(context != null)
        showSnackbar(context, text?:context.getString(R.string.error_placeholder),
                parentLayout, R.color.mapStatusInvisible)
    }

    fun showSuccess(context: Context?, text: String?, parentLayout: View) {
        if(context != null)
        showSnackbar(context, text?:context.getString(R.string.error_placeholder),
                parentLayout, R.color.mapStatusVisible)
    }

    fun showSuccess(context: Context?, textResource: Int, parentLayout: View) {
        showSuccess(context, context?.getString(textResource), parentLayout)
    }

    fun showError(context: Context?, textResource: Int, parentLayout: View) {
        showError(context, context?.getString(textResource), parentLayout)
    }

    private fun showSnackbar(context: Context, text: String,
                             parentLayout: View, color: Int) {
        val snackbar = Snackbar.make(parentLayout, text, Snackbar.LENGTH_LONG)
        snackbar.view.setBackgroundColor(ContextCompat.getColor(
                context, color))
        snackbar.show()
    }
}
