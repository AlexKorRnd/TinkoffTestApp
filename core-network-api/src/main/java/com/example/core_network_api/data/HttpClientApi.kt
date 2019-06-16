package com.example.core_network_api.data

import android.content.Context
import retrofit2.Retrofit

interface HttpClientApi {

    fun provideClient(context: Context): Retrofit

}