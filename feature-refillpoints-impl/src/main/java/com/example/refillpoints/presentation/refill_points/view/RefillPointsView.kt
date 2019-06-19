package com.example.refillpoints.presentation.refill_points.view

import com.example.refillpoints.domain.models.RefillPointModel

interface RefillPointsView {

    fun showRefillPoints(points: List<RefillPointModel>)
    fun showError(error: Throwable)
}