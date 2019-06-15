package com.example.alexkorrnd.tinkofftestapp.di.app

import android.content.Context
import com.example.alexkorrnd.tinkofftestapp.MainApp
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun provideContext(): Context {
        return MainApp.getAppContext()
    }

}