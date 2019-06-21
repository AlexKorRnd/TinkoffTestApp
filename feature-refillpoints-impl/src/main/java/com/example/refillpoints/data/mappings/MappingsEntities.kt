package com.example.refillpoints.data.mappings

import com.example.refillpoints.data.db.models.*
import com.example.refillpoints.data.network.responses.*
import com.example.refillpoints.domain.models.LocationModel
import com.example.refillpoints.domain.models.PartnerModel
import com.example.refillpoints.domain.models.RefillPointModel

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

fun DailyLimitResponse.toEntity(partnerEntity: PartnerEntity?, currencyEntity: CurrencyEntity) =
    DailyLimitEntity(partnerEntity, currencyEntity, amount)

fun LimitResponse.toEntity(partnerEntity: PartnerEntity?, currencyEntity: CurrencyEntity) = LimitEntity(partnerEntity, currencyEntity, min, max)

fun LocationResponse.toEntity() = LocationEntity(latitude, longitude)


fun PartnerModel.toEntity() = PartnerEntity(
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

fun LocationModel.toEntity() = LocationEntity(latitude, longitude)


fun RefillPointModel.toEntity(area: PointsInAreaEntities,
                              partner: PartnerEntity,
                              location: LocationEntity
): RefillPointEntity = RefillPointEntity(
    externalId,
    workHours,
    phones,
    addressInfo,
    fullAddress,
    partner,
    location,
    area
)
