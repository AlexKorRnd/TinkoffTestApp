package com.example.refillpoints.domain.models

data class ScreenRect(
    val topLeft: LocationModel,
    val topRight: LocationModel,
    val bottomRight: LocationModel,
    val bottomLeft: LocationModel
)