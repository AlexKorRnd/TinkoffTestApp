package com.example.refillpoints.data.network.responses

data class RefillPointsResponse(
    val addressInfo: String,
    val externalId: String,
    val fullAddress: String,
    val location: LocationResponse,
    val partnerName: String,
    val phones: String,
    val workHours: String
)

data class LocationResponse(
    val latitude: Double,
    val longitude: Double
)