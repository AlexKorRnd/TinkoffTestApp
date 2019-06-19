package com.example.alexkorrnd.tinkofftestapp.di

import com.example.core_network_impl.di.CoreNetworkComponent
import com.example.feature_refillpoints_api.RefillPointsFeatureApi
import com.example.refillpoints.di.DaggerRefillPointsFeatureComponent_RefillPointsFeatureDependenciesComponent
import com.example.refillpoints.di.RefillPointsFeatureComponent

object FeatureProxyInjector {

    fun initFeatureRefillPoints() {
        featureRefillPoints()
    }

    fun featureRefillPoints(): RefillPointsFeatureApi {
        return RefillPointsFeatureComponent.initAndGet(
            DaggerRefillPointsFeatureComponent_RefillPointsFeatureDependenciesComponent.builder()
                .coreNetworkApi(CoreNetworkComponent.get())
                .build()
        )
    }

}