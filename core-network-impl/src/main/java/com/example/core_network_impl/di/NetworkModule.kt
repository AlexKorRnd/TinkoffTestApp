package com.example.core_network_impl.di

import com.example.core_network_api.data.HttpClientApi
import com.example.core_network_impl.data.HttpClientImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class NetworkModule {

    @Singleton
    @Binds
    abstract fun provideHttpClientApi(httpClient: HttpClientImpl): HttpClientApi

}