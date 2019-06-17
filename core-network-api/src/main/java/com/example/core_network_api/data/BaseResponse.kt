package com.example.core_network_api.data

class BaseResponse<out T>(
    val resultCode: String?,
    val payload: T?,
    val trackingId: String?,
    val errorMessage: String?,
    val plainMessage: String?
) {

    val isSuccess: Boolean
        get() = resultCode == "OK"

}