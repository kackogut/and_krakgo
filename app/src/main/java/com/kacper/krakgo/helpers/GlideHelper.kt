package com.kacper.krakgo.helpers

import android.content.Context
import android.graphics.drawable.Drawable
import android.media.Image
import android.net.Uri
import android.support.v4.app.Fragment
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar

import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.signature.MediaStoreSignature
import com.kacper.krakgo.KrakGoApp
import com.kacper.krakgo.R

/**
 * Created by kacper on 17/01/2018.
 */

object GlideHelper {

    private val myOptions = RequestOptions()
            .placeholder(R.drawable.ic_user_logo_register)
            .signature(MediaStoreSignature("",
                    System.currentTimeMillis(), 0))

    fun loadWithProgress(context: Context?, imageView: ImageView, progressBar: ProgressBar, photoUri: Uri) {
        progressBar.visibility = View.VISIBLE
        imageView.isEnabled = false


        if(context!= null) {
            Glide.with(context)
                    .load(photoUri)
                    .apply(myOptions)
                    .listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(e: GlideException?,
                                                  model: Any, target: Target<Drawable>,
                                                  isFirstResource: Boolean): Boolean {
                            progressBar.visibility = View.GONE
                            imageView.isEnabled = true
                            return false
                        }

                        override fun onResourceReady(resource: Drawable, model: Any,
                                                     target: Target<Drawable>, dataSource: DataSource,
                                                     isFirstResource: Boolean): Boolean {
                            progressBar.visibility = View.GONE
                            imageView.isEnabled = true
                            return false
                        }
                    })
                    .into(imageView)
        }
    }

    fun load(imageView: ImageView, photoUrl: String) {
        Glide.with(imageView.context)
                .load(photoUrl)
                .into(imageView)
    }

}
