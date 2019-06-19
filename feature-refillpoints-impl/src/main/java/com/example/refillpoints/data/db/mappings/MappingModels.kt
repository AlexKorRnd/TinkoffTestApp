package com.example.refillpoints.data.db.mappings

import com.example.refillpoints.data.db.models.PartnerEntity
import com.example.refillpoints.data.network.responses.LocationResponse
import com.example.refillpoints.data.network.responses.PartnerResponse
import com.example.refillpoints.data.network.responses.RefillPointsResponse
import com.example.refillpoints.domain.models.LocationModel
import com.example.refillpoints.domain.models.PartnerModel
import com.example.refillpoints.domain.models.RefillPointModel

fun PartnerEntity.toModel() = PartnerModel(
        id = id,
        name = name,
        picture = picture,
        url = url,
        depositionDuration = depositionDuration,
        limitations = limitations,
        description = description
        )

fun PartnerResponse.toModel() = PartnerModel(
        id = id,
        name = name,
        picture = picture,
        url = url,
        depositionDuration = depositionDuration,
        limitations = limitations,
        description = description
)

fun LocationResponse.toModel() = LocationModel(latitude, longitude)

fun RefillPointsResponse.toModel(partnerModel: PartnerModel) = RefillPointModel(
        partner = partnerModel,
        addressInfo = addressInfo,
        externalId = externalId,
        fullAddress = fullAddress,
        location = location.toModel(),
        phones = phones,
        workHours = workHours
)