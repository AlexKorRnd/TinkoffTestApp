package com.example.core.base.extensions

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
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

fun Context.browse(url: String, newTask: Boolean = false): Boolean {
    try {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        if (newTask) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        startActivity(intent)
        return true
    } catch (e: ActivityNotFoundException) {
        e.printStackTrace()
        return false
    }
}

fun Context.makeDial(phone: String): Boolean {
    try {
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phone"))
        startActivity(intent)
        return true
    } catch (e: ActivityNotFoundException) {
        e.printStackTrace()
        return false
    }
}