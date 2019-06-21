package com.example.refillpoints.data

import android.location.Location
import com.example.refillpoints.domain.models.LocationModel

object MathUtils {

    fun calculateRadius(startPoint: LocationModel, endPoint: LocationModel): Float {
        val startLocation = Location("").apply {
            latitude = startPoint.latitude
            longitude = startPoint.longitude
        }

        val endLocation = Location("").apply {
            latitude = endPoint.latitude
            longitude = endPoint.longitude
        }

        return startLocation.distanceTo(endLocation)
    }
}