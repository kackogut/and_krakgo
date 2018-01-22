package com.kacper.and_krakgo.helpers;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

/**
 * Created by kacper on 29/10/2017.
 */

public class PhotoHelper {
    public static Bitmap getImageFromGallery(Context context, Uri photoUri){
        String[] filePathColumn = { MediaStore.Images.Media.DATA };
        Cursor cursor = context.getContentResolver().query(photoUri,filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String picturePath = cursor.getString(columnIndex);
        cursor.close();
        Bitmap bm = BitmapFactory.decodeFile(picturePath);
        return bm;
    }
    public static void startCircleCropPhoto(Fragment framgment){
        CropImage.activity()
                .setCropShape(CropImageView.CropShape.OVAL)
                .setAspectRatio(1, 1)
                .start(framgment.getContext(), framgment);
    }
}
