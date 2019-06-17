package com.example.refillpoints.data.network.responses

data class RefillPointsResponse(
    val addressInfo: String,
    val externalId: String,
    val fullAddress: String,
    val location: Location,
    val partnerName: String,
    val phones: String,
    val workHours: String
)

data class Location(
    val latitude: Double,
    val longitude: Double
)