package com.example.refillpoints.presentation.refill_points.presenter

import com.example.refillpoints.domain.models.LocationModel
import com.example.refillpoints.domain.models.RefillPointModel
import com.example.refillpoints.presentation.refill_points.view.RefillPointsMapView
import java.lang.ref.WeakReference

class RefillPointsMapPresenter(
    refillPointMapView: RefillPointsMapView
) {

    private val view = WeakReference(refillPointMapView)

    private var refillPointModels = mutableListOf<RefillPointModel>()
    var myLocation: LocationModel? = null
    var isDefault = true

    fun saveRefillPoints(points: List<RefillPointModel>) {
        refillPointModels.clear()
        refillPointModels.addAll(points)
    }

    fun showRefillPointInfoById(id: String?) {
        refillPointModels.forEach {
            if (it.externalId == id) {
                view.get()?.showPointInfo(it)
                return@forEach
            }
        }
    }
}