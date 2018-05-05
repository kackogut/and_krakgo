package com.kacper.krakgo.helpers;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.signature.MediaStoreSignature;
import com.kacper.krakgo.KrakGoApp;
import com.kacper.krakgo.R;

/**
 * Created by kacper on 17/01/2018.
 */

public class GlideHelper {
    public static void loadWithProgress(Context context, final ImageView imageView, final ProgressBar progressBar, Uri photoUri){
        progressBar.setVisibility(View.VISIBLE);
        imageView.setEnabled(false);
        RequestOptions myOptions = new RequestOptions()
                .placeholder(R.drawable.ic_user_logo_register)
                .signature(new MediaStoreSignature("", System.currentTimeMillis(), 0));

        Glide.with(context)
                .load(photoUri)
                .apply(myOptions)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        imageView.setEnabled(true);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        imageView.setEnabled(true);
                        return false;
                    }
                })
                .into(imageView);
    }

    public static void load(ImageView imageView, String photoUrl){
        Glide.with(imageView.getContext())
                .load(photoUrl)
                .into(imageView);
    }

}
