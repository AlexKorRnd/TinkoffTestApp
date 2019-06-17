package com.example.refillpoints.domain

import com.example.refillpoints.data.network.responses.Location
import com.example.refillpoints.data.network.responses.RefillPointsResponse
import io.reactivex.Single

interface RefillPointsInteractor {

    fun loadRefillPoints(lat: Double, lng: Double, radius: Int): Single<List<RefillPointsResponse>>

    fun calculateRadius(topLeft: Location, topRight: Location, bottomRight: Location, bottomLeft: Location): Single<Int>
}