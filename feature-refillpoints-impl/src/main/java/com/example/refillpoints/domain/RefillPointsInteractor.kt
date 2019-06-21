package com.example.refillpoints.domain

import com.example.refillpoints.domain.models.LocationModel
import com.example.refillpoints.domain.models.RefillPointModel
import com.example.refillpoints.domain.models.ScreenRect
import io.reactivex.Single

interface RefillPointsInteractor {

    fun loadRefillPoints(center: LocationModel, screenRect: ScreenRect): Single<List<RefillPointModel>>

    fun updateRefillPoint(refillPointModel: RefillPointModel): Single<RefillPointModel>
}