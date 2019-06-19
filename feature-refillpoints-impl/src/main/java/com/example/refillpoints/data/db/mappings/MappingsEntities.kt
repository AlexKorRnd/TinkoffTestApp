package com.example.refillpoints.data.db.mappings

import com.example.refillpoints.data.db.models.*
import com.example.refillpoints.data.network.responses.*

fun PartnerResponse.toEntity(): PartnerEntity =
    PartnerEntity(
        id,
        name,
        picture,
        url,
        hasLocations,
        isMomentary,
        depositionDuration,
        limitations,
        pointType,
        description,
        moneyMin,
        moneyMax,
        hasPreferentialDeposition
    )

fun CurrencyResponse.toEntity() = CurrencyEntity(code, name)

fun DailyLimitResponse.toEntity(partnerEntity: PartnerEntity?) =
    DailyLimitEntity(partnerEntity, currency.toEntity(), amount)

fun LimitResponse.toEntity(partnerEntity: PartnerEntity?) = LimitEntity(partnerEntity, currency.toEntity(), min, max)

fun LocationResponse.toEntity() = LocationEntity(latitude, longitude)

fun RefillPointsResponse.toEntity(partnerEntity: PartnerEntity?, locationEntity: LocationEntity?) = RefillPointEntity(
    externalId,
    workHours,
    phones,
    addressInfo,
    fullAddress,
    partnerEntity,
    locationEntity
)