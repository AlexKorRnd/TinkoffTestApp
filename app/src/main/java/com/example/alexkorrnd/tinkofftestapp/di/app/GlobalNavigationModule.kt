package com.example.alexkorrnd.tinkofftestapp.di.app

import android.content.Context
import com.example.alexkorrnd.tinkofftestapp.routing.GlobalNavigator
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class GlobalNavigationModule {

    @Singleton
    @Provides
    fun provideNavigator(context: Context): GlobalNavigator {
        return GlobalNavigator(context)
    }
}