package com.example.core_network_impl.data

import com.example.core_network_api.data.HttpClientApi
import com.example.core_network_api.data.ResponseValidator
import com.example.core_network_impl.BuildConfig
import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class HttpClientImpl @Inject constructor(): HttpClientApi {

    companion object {
        private const val REST_API_URL = "https://api.tinkoff.ru/"
    }

    override fun provideClient(): Retrofit {
        val okHttpClient = OkHttpClient.Builder()

        if (BuildConfig.DEBUG) {
            okHttpClient.addNetworkInterceptor(StethoInterceptor())
        }

        return Retrofit.Builder()
            .baseUrl(REST_API_URL)
            .client(okHttpClient.build())
            .addConverterFactory(GsonConverterFactory.create())
            .validateEagerly(BuildConfig.DEBUG)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    override fun provideResponseValidator(): ResponseValidator {
        return ResponseValidator
    }
}