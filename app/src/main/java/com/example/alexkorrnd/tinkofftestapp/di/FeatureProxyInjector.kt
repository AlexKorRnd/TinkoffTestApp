package com.example.alexkorrnd.tinkofftestapp.di

import com.example.feature_refillpoints_api.RefillPointsFeatureApi
import com.example.refillpoints.di.DaggerRefillPointsFeatureComponent_RefillPointsFeatureDependenciesComponent
import com.example.refillpoints.di.RefillPointsFeatureComponent

object FeatureProxyInjector {

    fun featureRefillPoints(): RefillPointsFeatureApi {
        return RefillPointsFeatureComponent.initAndGet(
            DaggerRefillPointsFeatureComponent_RefillPointsFeatureDependenciesComponent
                .builder()
                .build()
        )
    }

}