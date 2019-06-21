package com.example.refillpoints.domain

import com.example.refillpoints.domain.models.LocationModel
import com.example.refillpoints.domain.models.RefillPointModel
import com.example.refillpoints.domain.models.ScreenRect
import io.reactivex.Single
import javax.inject.Inject

class RefillPointsInteractorImpl @Inject constructor(
        private val refillPointsRepository: RefillPointsRepository
) : RefillPointsInteractor {

    override fun loadRefillPoints(center: LocationModel, screenRect: ScreenRect): Single<List<RefillPointModel>> {
        return refillPointsRepository.loadRefillPoints(center, screenRect)
    }

    override fun updateRefillPoint(refillPointModel: RefillPointModel): Single<RefillPointModel> {
        return refillPointsRepository.updateRefillPoint(refillPointModel)
    }
}