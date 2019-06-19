package com.example.refillpoints.presentation.refill_points.presenter

import com.example.refillpoints.domain.models.RefillPointModel
import com.example.refillpoints.presentation.refill_points.view.RefillPointsMapView
import timber.log.Timber
import java.lang.ref.WeakReference

class RefillPointsMapPresenter(
    refillPointMapView: RefillPointsMapView
) {

    private val view = WeakReference(refillPointMapView)

    private var refillPointModels = mutableListOf<RefillPointModel>()

    fun saveRefillPoints(points: List<RefillPointModel>) {
        refillPointModels.clear()
        refillPointModels.addAll(points)
    }

    fun showRefillPointInfoById(id: String?) {
        Timber.d("showRefillPointInfoByHash:: id = $id")
        refillPointModels.forEach {
            Timber.i("showRefillPointInfoByHash:: it.id = ${it.externalId}, id = $id")
            if (it.externalId == id) {
                Timber.v("showPointInfo!!!")
                view.get()?.showPointInfo(it)
                return@forEach
            }
        }
    }
}