package com.example.alexkorrnd.tinkofftestapp.routing

import android.content.Context
import com.example.alexkorrnd.tinkofftestapp.di.FeatureProxyInjector
import javax.inject.Singleton

@Singleton
class GlobalNavigator(
    private val context: Context
) : Navigator {

    fun showRefillPoints() {
        FeatureProxyInjector.featureRefillPoints().refillPointsStarter().start(context)
    }
}