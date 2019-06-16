package com.example.core_network_impl.di

import com.example.core_network_api.di.CoreNetworkApi
import dagger.Component
import javax.inject.Singleton

@Component(modules = [NetworkModule::class])
@Singleton
abstract class CoreNetworkComponent: CoreNetworkApi {

    companion object {
        @Volatile
        private var component: CoreNetworkComponent? = null

        fun get(): CoreNetworkComponent {
            if (component == null) {
                synchronized(CoreNetworkComponent::class) {
                    if (component == null) {
                        component = DaggerCoreNetworkComponent.builder().build()
                    }
                }
            }
            return component!!
        }
    }



}