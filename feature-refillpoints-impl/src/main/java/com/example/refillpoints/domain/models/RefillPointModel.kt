package com.example.refillpoints.domain.models

import com.example.refillpoints.data.network.responses.Location

data class RefillPointModel(
    val partner: PartnerModel,
    val addressInfo: String?,
    val externalId: String,
    val fullAddress: String,
    val location: Location,
    val phones: String?,
    val workHours: String?
)