package com.example.refillpoints.domain

import com.example.refillpoints.domain.models.LocationModel
import com.example.refillpoints.domain.models.RefillPointModel
import io.reactivex.Single

interface RefillPointsInteractor {

    fun loadRefillPoints(lat: Double, lng: Double, radius: Int): Single<List<RefillPointModel>>

    fun loadRefillPoints(lat: Double,
                         lng: Double,
                         topLeft: LocationModel,
                         topRight: LocationModel,
                         bottomRight: LocationModel,
                         bottomLeft: LocationModel
    ): Single<List<RefillPointModel>>

    fun updateRefillPoint(refillPointModel: RefillPointModel): Single<RefillPointModel>
}