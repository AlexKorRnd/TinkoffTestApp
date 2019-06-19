package com.example.refillpoints.di

import com.example.core.di.PerFeature
import com.example.core_network_api.di.CoreNetworkApi
import com.example.feature_refillpoints_api.RefillPointsFeatureApi
import com.example.refillpoints.presentation.refill_points.view.RefillPointsActivity
import dagger.Component

@Component(
    modules = [
        RefillPointsFeatureModule::class
    ],
    dependencies = [RefillPointsFeatureDependencies::class]
)
@PerFeature
abstract class RefillPointsFeatureComponent: RefillPointsFeatureApi {

    companion object {
        @Volatile
        private var refillPointsFeatureComponent: RefillPointsFeatureComponent? = null

        fun initAndGet(refillPointsFeatureDependencies: RefillPointsFeatureDependencies): RefillPointsFeatureComponent {
            if (refillPointsFeatureComponent == null) {
                synchronized(RefillPointsFeatureComponent::class.java) {
                    if (refillPointsFeatureComponent == null) {
                        refillPointsFeatureComponent = DaggerRefillPointsFeatureComponent
                            .builder()
                            .refillPointsFeatureDependencies(refillPointsFeatureDependencies)
                            .build()
                    }
                }
            }
            return refillPointsFeatureComponent!!
        }

        fun get(): RefillPointsFeatureComponent {
            val refillPointsFeatureComponent = refillPointsFeatureComponent
            if (refillPointsFeatureComponent == null) {
                throw RuntimeException("You must call 'initAndGet(AntitheftFeatureDependencies antitheftFeatureDependencies)' method")
            }
            return refillPointsFeatureComponent
        }
    }




    abstract fun inject(activity: RefillPointsActivity)

    abstract fun refillPointsScreenComponent(): RefillPointsScreenComponent

    @Component(
        dependencies = [
            CoreNetworkApi::class
        ]
    )
    @PerFeature
    interface RefillPointsFeatureDependenciesComponent: RefillPointsFeatureDependencies {}
}