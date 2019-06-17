package com.example.refillpoints.di

import com.example.core_network_api.data.HttpClientApi

interface RefillPointsFeatureDependencies {

    fun httpClient(): HttpClientApi

}