package com.example.core_network_impl.data

import android.content.Context
import com.example.core_network_api.data.HttpClientApi
import com.example.core_network_impl.BuildConfig
import com.facebook.stetho.Stetho
import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class HttpClientImpl @Inject constructor(): HttpClientApi {

    companion object {
        private const val REST_API_URL = "https://api.tinkoff.ru/v1/"
    }

    override fun provideClient(context: Context): Retrofit {
        val okHttpClient = OkHttpClient.Builder()

        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(context)
            okHttpClient.addNetworkInterceptor(StethoInterceptor())
        }

        return Retrofit.Builder()
            .baseUrl(REST_API_URL)
            .client(okHttpClient.build())
            .addConverterFactory(GsonConverterFactory.create())
            .validateEagerly(BuildConfig.DEBUG)
            .build()
    }
}