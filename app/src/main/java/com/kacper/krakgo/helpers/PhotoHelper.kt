package com.kacper.krakgo.helpers

import android.support.v4.app.Fragment

import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView

/**
 * Created by kacper on 29/10/2017.
 */

object PhotoHelper {

    fun startCircleCropPhoto(fragment: Fragment) {
        CropImage.activity()
                .setCropShape(CropImageView.CropShape.OVAL)
                .setAspectRatio(1, 1)
                .start(fragment.context!!, fragment)
    }
}
