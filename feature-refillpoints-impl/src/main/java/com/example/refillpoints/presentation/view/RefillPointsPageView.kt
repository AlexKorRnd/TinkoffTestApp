package com.example.refillpoints.presentation.view

import com.example.refillpoints.data.network.responses.RefillPointsResponse

interface RefillPointsPageView {

    fun showRefillPoints(points: List<RefillPointsResponse>)

}