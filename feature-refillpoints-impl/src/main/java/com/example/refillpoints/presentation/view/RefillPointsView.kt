package com.example.refillpoints.presentation.view

import com.example.refillpoints.data.network.responses.RefillPointsResponse

interface RefillPointsView {

    fun showRefillPoints(points: List<RefillPointsResponse>)
    fun showError(error: Throwable)
}