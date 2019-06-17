package com.example.refillpoints.presentation.view

import com.example.refillpoints.data.network.responses.RefillPointsResponse

class RefillPointsMapPresenter {

    var points: List<RefillPointsResponse> = emptyList()
        private set

    var needShowPointsWhenMapWillBeReady: Boolean = false

    fun showPointsWhenMapWillBeReady(points: List<RefillPointsResponse>) {
        this.points = points
        needShowPointsWhenMapWillBeReady = true
    }
}