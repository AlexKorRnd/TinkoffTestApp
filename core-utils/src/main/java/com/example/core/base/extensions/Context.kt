package com.example.core.base.extensions

import android.content.Context
import android.util.DisplayMetrics

fun Context.screenDensityName(): String {
    return when(resources.displayMetrics.densityDpi) {
        DisplayMetrics.DENSITY_MEDIUM -> "mdpi"
        DisplayMetrics.DENSITY_HIGH -> "hdpi"
        DisplayMetrics.DENSITY_XHIGH -> "xdpi"
        DisplayMetrics.DENSITY_XXHIGH -> "xxdpi"
        DisplayMetrics.DENSITY_XXXHIGH -> "xxxdpi"
        else -> "hdpi"
    }
}