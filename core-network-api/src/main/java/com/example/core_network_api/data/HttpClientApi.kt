package com.example.core_network_api.data

import retrofit2.Retrofit

interface HttpClientApi {

    fun provideClient(): Retrofit

    fun provideResponseValidator(): ResponseValidator
}