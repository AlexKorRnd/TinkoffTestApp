package com.example.refillpoints.domain

import com.example.refillpoints.domain.models.RefillPointModel
import io.reactivex.Single

interface RefillPointsRepository {
    /**
    *@param radius - радиус в метрах
    * */
    fun loadRefillPoints(lat: Double, lng: Double, radius: Int): Single<List<RefillPointModel>>

    fun updateRefillPoint(refillPointModel: RefillPointModel): Single<RefillPointModel>
}