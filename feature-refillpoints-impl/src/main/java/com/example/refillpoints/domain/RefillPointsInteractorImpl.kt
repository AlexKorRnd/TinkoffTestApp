package com.example.refillpoints.domain

import android.location.Location
import com.example.refillpoints.domain.models.LocationModel
import com.example.refillpoints.domain.models.RefillPointModel
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class RefillPointsInteractorImpl @Inject constructor(
        private val refillPointsRepository: RefillPointsRepository
) : RefillPointsInteractor {

    override fun loadRefillPoints(lat: Double, lng: Double, radius: Int): Single<List<RefillPointModel>> {
        return refillPointsRepository.loadRefillPoints(lat, lng, radius)
    }

    override fun loadRefillPoints(
            lat: Double,
            lng: Double,
            topLeft: LocationModel,
            topRight: LocationModel,
            bottomRight: LocationModel,
            bottomLeft: LocationModel
    ): Single<List<RefillPointModel>> {

        return calculateRadius(topLeft, topRight, bottomRight, bottomLeft)

                .flatMap { radius ->
                    loadRefillPoints(lat, lng, radius)
                }
    }

    private fun calculateRadius(
            topLeft: LocationModel,
            topRight: LocationModel,
            bottomRight: LocationModel,
            bottomLeft: LocationModel
    ): Single<Int> {
        return Single.fromCallable {
            val distanceWidth = FloatArray(1)
            android.location.Location.distanceBetween(
                    (topRight.latitude + bottomRight.latitude) / 2,
                    (topRight.longitude + bottomRight.longitude) / 2,
                    (topLeft.latitude + bottomLeft.latitude) / 2,
                    (topLeft.longitude + bottomLeft.longitude) / 2,
                    distanceWidth
            )


            val distanceHeight = FloatArray(1)
            android.location.Location.distanceBetween(
                    (topRight.latitude + bottomRight.latitude) / 2,
                    (topRight.longitude + bottomRight.longitude) / 2,
                    (topLeft.latitude + bottomLeft.latitude) / 2,
                    (topLeft.longitude + bottomLeft.longitude) / 2,
                    distanceHeight
            )

            val radius: Float

            if (distanceWidth[0] > distanceHeight[0]) {
                radius = distanceWidth[0]
            } else {
                radius = distanceHeight[0]
            }
            Math.round(radius)
        }
    }

    override fun updateRefillPoint(refillPointModel: RefillPointModel): Single<RefillPointModel> {
        return refillPointsRepository.updateRefillPoint(refillPointModel)
    }
}