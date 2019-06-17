package com.example.core_network_api.data

class ApiException(
    val trackingId: String?,
    val errorMessage: String?,
    val plainMessage: String?
): RuntimeException()