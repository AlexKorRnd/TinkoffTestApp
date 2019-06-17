package com.example.refillpoints.di

import com.example.core.di.PerFeature
import com.example.feature_refillpoints_api.RefillPointsStarter
import com.example.refillpoints.data.RefillPointsRepositoryImpl
import com.example.refillpoints.domain.RefillPointsInteractor
import com.example.refillpoints.domain.RefillPointsInteractorImpl
import com.example.refillpoints.domain.RefillPointsRepository
import com.example.refillpoints.start.RefillPointsStarterImpl
import dagger.Binds
import dagger.Module

@Module
abstract class RefillPointsFeatureModule {

    @PerFeature
    @Binds
    abstract fun provideRefillPointsRepository(repositoryImpl: RefillPointsRepositoryImpl): RefillPointsRepository

    @PerFeature
    @Binds
    abstract fun provideRefillInteractor(refillPointsInteractor: RefillPointsInteractorImpl): RefillPointsInteractor

    @PerFeature
    @Binds
    abstract fun provideRefillPointsStarter(refillPointsStarter: RefillPointsStarterImpl): RefillPointsStarter

    /*@PerFeature
    @Binds
    abstract fun provideRefillPointsView(refillPointsActivity: RefillPointsActivity): RefillPointsView*/
}