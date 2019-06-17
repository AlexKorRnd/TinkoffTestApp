package com.example.refillpoints.domain

import com.example.refillpoints.data.network.responses.PartnerResponse
import com.example.refillpoints.data.network.responses.RefillPointsResponse
import io.reactivex.Single

interface RefillPointsRepository {

    fun loadPartners(): Single<List<PartnerResponse>>

    /**
    *@param radius - радиус в метрах
    * */
    fun loadRefillPoints(lat: Double, lng: Double, radius: Int): Single<List<RefillPointsResponse>>
}