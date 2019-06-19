package com.example.alexkorrnd.tinkofftestapp.routing

import android.app.Activity
import com.example.alexkorrnd.tinkofftestapp.di.FeatureProxyInjector
import javax.inject.Singleton

@Singleton
class GlobalNavigator : Navigator {

    fun showRefillPoints(activity: Activity) {
        FeatureProxyInjector.featureRefillPoints().refillPointsStarter().start(activity)
    }
}