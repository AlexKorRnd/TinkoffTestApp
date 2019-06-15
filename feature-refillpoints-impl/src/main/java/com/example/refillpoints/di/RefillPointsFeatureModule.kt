package com.example.refillpoints.di

import com.example.core.di.PerFeature
import com.example.feature_refillpoints_api.RefillPointsStarter
import com.example.refillpoints.start.RefillPointsStarterImpl
import dagger.Binds
import dagger.Module

@Module
abstract class RefillPointsFeatureModule {

    @PerFeature
    @Binds
    abstract fun provideRefillPointsStarter(refillPointsStarter: RefillPointsStarterImpl): RefillPointsStarter
}