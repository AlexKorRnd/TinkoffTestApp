package com.example.refillpoints.domain

import com.example.refillpoints.data.network.responses.Location
import com.example.refillpoints.domain.models.RefillPointModel
import io.reactivex.Single

interface RefillPointsInteractor {

    fun loadRefillPoints(lat: Double, lng: Double, radius: Int): Single<List<RefillPointModel>>

    fun loadRefillPoints(lat: Double,
                         lng: Double,
                         topLeft: Location,
                         topRight: Location,
                         bottomRight: Location,
                         bottomLeft: Location
    ): Single<List<RefillPointModel>>
}