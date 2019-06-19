package com.example.refillpoints.presentation.refill_points.view

import com.example.refillpoints.domain.models.LocationModel
import com.example.refillpoints.domain.models.RefillPointModel

interface RefillPointsMapView {

    fun showPointInfo(refillPointModel: RefillPointModel)

    fun showMyLocation(locationModel: LocationModel, isDefault: Boolean = false)
}