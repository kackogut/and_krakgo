package com.kacper.krakgo.helpers

import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.widget.ImageView

import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView

/**
 * Created by kacper on 29/10/2017.
 */

object PhotoHelper {
    fun getImageFromGallery(context: Context, photoUri: Uri): Bitmap {
        val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = context.contentResolver.query(photoUri, filePathColumn, null, null, null)
        cursor!!.moveToFirst()
        val columnIndex = cursor.getColumnIndex(filePathColumn[0])
        val picturePath = cursor.getString(columnIndex)
        cursor.close()
        return BitmapFactory.decodeFile(picturePath)
    }

    fun startCircleCropPhoto(framgment: Fragment) {
        CropImage.activity()
                .setCropShape(CropImageView.CropShape.OVAL)
                .setAspectRatio(1, 1)
                .start(framgment.context!!, framgment)
    }
}
