package com.example.core.base.extensions

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.core.content.ContextCompat
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat

fun Int.loadVector(context: Context): Drawable = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
    ContextCompat.getDrawable(context, this)!!
else
    VectorDrawableCompat.create(context.resources, this, context.theme) as Drawable