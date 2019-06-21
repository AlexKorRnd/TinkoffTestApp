@file:JvmName("ContextExt")
package com.example.core.base.extensions

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.util.DisplayMetrics
import android.util.Log

private const val TAG = "ContextExt"

fun Context.screenDensityName(): String {
    return when(resources.displayMetrics.densityDpi) {
        DisplayMetrics.DENSITY_MEDIUM -> "mdpi"
        DisplayMetrics.DENSITY_HIGH -> "hdpi"
        DisplayMetrics.DENSITY_XHIGH -> "xhdpi"
        DisplayMetrics.DENSITY_XXHIGH -> "xxhdpi"
        else -> "xxhdpi"
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
        Log.e(TAG, e.message, e)
        return false
    }
}

fun Context.makeDial(phone: String): Boolean {
    try {
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phone"))
        startActivity(intent)
        return true
    } catch (e: ActivityNotFoundException) {
        Log.e(TAG, e.message, e)
        return false
    }
}

fun Context.openAppSettings(activity: Activity): Boolean {
    try {
        val intent = Intent().apply {
            action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            data = Uri.parse("package:" + activity.packageName)
        }
        startActivity(intent)
        return true
    } catch (e: ActivityNotFoundException) {
        Log.e(TAG, e.message, e)
        return false
    }
}