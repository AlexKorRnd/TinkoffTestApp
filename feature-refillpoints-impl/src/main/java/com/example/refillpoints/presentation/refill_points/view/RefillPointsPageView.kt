package com.example.refillpoints.presentation.refill_points.view

import com.example.refillpoints.domain.models.RefillPointModel

interface RefillPointsPageView {

    fun showRefillPoints(points: List<RefillPointModel>)
    fun showUpdatedRefillPointSeenStatus(point: RefillPointModel)
}