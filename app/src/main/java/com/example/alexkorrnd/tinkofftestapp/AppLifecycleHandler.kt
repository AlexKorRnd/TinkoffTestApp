package com.example.alexkorrnd.tinkofftestapp

import android.app.Activity
import android.app.Application
import android.content.ComponentCallbacks2
import android.content.res.Configuration
import android.os.Bundle

class AppLifecycleHandler(
    private val lifecycleDelegate: LifeCycleDelegate
) : Application.ActivityLifecycleCallbacks,
    ComponentCallbacks2
{

    private var appInForeground = false

    override fun onActivityResumed(activity: Activity?) {
        if (!appInForeground) {
            appInForeground = true
            lifecycleDelegate.onAppForegrounded()
        }
    }

    override fun onTrimMemory(level: Int) {
        if (level == ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN) {
            lifecycleDelegate.onAppBackgrounded()
        }
    }




    override fun onActivityPaused(activity: Activity?) {
        /*no-op*/
    }

    override fun onActivityStarted(activity: Activity?) {
        /*no-op*/
    }

    override fun onActivityDestroyed(activity: Activity?) {
        /*no-op*/
    }

    override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
        /*no-op*/
    }

    override fun onActivityStopped(activity: Activity?) {
        /*no-op*/
    }

    override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
        /*no-op*/
    }

    override fun onLowMemory() {
        /*no-op*/
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        /*no-op*/
    }
}