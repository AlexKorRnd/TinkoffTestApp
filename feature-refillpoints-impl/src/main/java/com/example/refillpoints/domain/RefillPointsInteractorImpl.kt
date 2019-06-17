package com.example.refillpoints.domain

import com.example.refillpoints.data.network.responses.Location
import com.example.refillpoints.data.network.responses.RefillPointsResponse
import io.reactivex.Single
import javax.inject.Inject

class RefillPointsInteractorImpl @Inject constructor(
    private val refillPointsRepository: RefillPointsRepository
): RefillPointsInteractor {

    override fun loadRefillPoints(lat: Double, lng: Double, radius: Int): Single<List<RefillPointsResponse>> {
        return refillPointsRepository.loadRefillPoints(lat, lng, radius)
    }

    override fun calculateRadius(
        topLeft: Location,
        topRight: Location,
        bottomRight: Location,
        bottomLeft: Location
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
}