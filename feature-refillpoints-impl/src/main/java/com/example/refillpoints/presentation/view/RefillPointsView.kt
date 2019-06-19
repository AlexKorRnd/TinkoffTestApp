package com.example.refillpoints.presentation.view

import com.example.refillpoints.domain.models.RefillPointModel

interface RefillPointsView {

    fun showRefillPoints(points: List<RefillPointModel>)
    fun showError(error: Throwable)
}