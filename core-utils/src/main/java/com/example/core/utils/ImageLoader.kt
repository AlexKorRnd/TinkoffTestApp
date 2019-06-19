package com.example.core.utils

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

object ImageLoader {

    fun load(imageView: ImageView, url: String?, isCircle: Boolean, placeholder: Drawable? = null) {
        var request = Glide.with(imageView.context)
            .load(url)
        if (isCircle) {
            request = request.apply(RequestOptions.circleCropTransform())
        }
        request
            .placeholder(placeholder)
            .into(imageView)
    }

}