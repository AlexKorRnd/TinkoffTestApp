package com.example.alexkorrnd.tinkofftestapp

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.example.alexkorrnd.tinkofftestapp.di.app.AppComponent
import com.example.alexkorrnd.tinkofftestapp.di.app.DaggerAppComponent

class MainApp: Application() {

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
    }

}