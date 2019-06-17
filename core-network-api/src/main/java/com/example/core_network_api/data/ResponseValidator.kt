package com.example.core_network_api.data

import io.reactivex.Single
import io.reactivex.SingleTransformer

object ResponseValidator {

    @Suppress("UNCHECKED_CAST")
    fun <T> networkTransformer() = transformer as SingleTransformer<BaseResponse<T>, T>

    private val transformer = SingleTransformer<BaseResponse<Any>, Any> { source: Single<BaseResponse<Any>> ->
        source.flatMap {
            val error = it.plainMessage
            if (error != null) {
                Single.error<BaseResponse<*>>(ApiException(
                    trackingId = it.trackingId,
                    errorMessage = it.errorMessage,
                    plainMessage = it.plainMessage
                ))
            } else {
                Single.just(it.payload)
            }
        }
    }

}