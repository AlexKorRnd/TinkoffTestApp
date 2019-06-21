package com.example.refillpoints.data.mappings

import com.example.refillpoints.data.db.models.LocationEntity
import com.example.refillpoints.data.db.models.PartnerEntity
import com.example.refillpoints.data.db.models.RefillPointEntity
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
        description = description,
        hasLocations = isHasLocations,
        hasPreferentialDeposition = isHasPreferentialDeposition,
        isMomentary = isMomentary,
        moneyMax = moneyMax,
        moneyMin = moneyMin,
        pointType = pointType
        )

fun LocationEntity.toModel() = LocationModel(latitude, longitude)

fun RefillPointEntity.toModel(isSeen: Boolean) = RefillPointModel(
        partner = partner.toModel(),
        addressInfo = addresInfo,
        externalId = externalId,
        fullAddress = fullAddress,
        location = location.toModel(),
        phones = phones,
        workHours = workHours,
        isSeen = isSeen
)

fun PartnerResponse.toModel() = PartnerModel(
        id = id,
        name = name,
        picture = picture,
        url = url,
        depositionDuration = depositionDuration,
        limitations = limitations,
        description = description,
        hasLocations = hasLocations,
        hasPreferentialDeposition = hasPreferentialDeposition,
        isMomentary = isMomentary,
        moneyMax = moneyMax,
        moneyMin = moneyMin,
        pointType = pointType
)

fun LocationResponse.toModel() = LocationModel(latitude, longitude)

fun RefillPointsResponse.toModel(partnerModel: PartnerModel, isSeen: Boolean) = RefillPointModel(
        partner = partnerModel,
        addressInfo = addressInfo,
        externalId = externalId,
        fullAddress = fullAddress,
        location = location.toModel(),
        phones = phones,
        workHours = workHours,
        isSeen = isSeen
)
