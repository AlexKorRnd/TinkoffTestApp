package com.example.alexkorrnd.tinkofftestapp.di.app

import com.example.alexkorrnd.tinkofftestapp.routing.GlobalNavigator
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class GlobalNavigationModule {

    @Singleton
    @Provides
    fun provideNavigator(): GlobalNavigator {
        return GlobalNavigator()
    }
}