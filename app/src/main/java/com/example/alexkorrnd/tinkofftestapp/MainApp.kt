package com.example.alexkorrnd.tinkofftestapp

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.example.alexkorrnd.tinkofftestapp.di.app.AppComponent
import com.example.alexkorrnd.tinkofftestapp.di.app.DaggerAppComponent
import com.example.core_network_impl.di.CoreNetworkComponent
import com.example.refillpoints.data.db.DatabaseHolder
import com.example.refillpoints.di.DaggerRefillPointsFeatureComponent_RefillPointsFeatureDependenciesComponent
import com.example.refillpoints.di.RefillPointsFeatureComponent
import com.facebook.stetho.Stetho
import timber.log.Timber

class MainApp: Application(), LifeCycleDelegate {

    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private lateinit var context: Context

        fun getAppContext(): Context {
            return context
        }
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext

        AppComponent.init(
            DaggerAppComponent.builder()
                .build()
        )
        AppComponent.get().inject(this)

        Stetho.initializeWithDefaults(applicationContext)
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        RefillPointsFeatureComponent.initAndGet(
            DaggerRefillPointsFeatureComponent_RefillPointsFeatureDependenciesComponent
                .builder()
                .coreNetworkApi(CoreNetworkComponent.get())
                .build()
        )

        DatabaseHolder.init(applicationContext)
        registerLifecycleHandler(AppLifecycleHandler(this))
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onAppBackgrounded() {
        DatabaseHolder.close()
    }

    override fun onAppForegrounded() {
        DatabaseHolder.init(applicationContext)
    }

    private fun registerLifecycleHandler(lifeCycleHandler: AppLifecycleHandler) {
        registerActivityLifecycleCallbacks(lifeCycleHandler)
        registerComponentCallbacks(lifeCycleHandler)
    }

}