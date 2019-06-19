package com.example.refillpoints.presentation.view

import com.example.refillpoints.domain.models.RefillPointModel

interface RefillPointsPageView {

    fun showRefillPoints(points: List<RefillPointModel>)

}