package com.example.refillpoints.presentation.refill_points.presenter

import com.example.refillpoints.domain.models.LocationModel
import com.example.refillpoints.presentation.refill_points.view.RefillPointsMapView
import com.google.android.gms.maps.model.Marker
import java.lang.ref.WeakReference

class RefillPointsMapPresenter(
        refillPointMapView: RefillPointsMapView
) {

    private val view = WeakReference(refillPointMapView)

    var myLocation: LocationModel? = null
    var isDefault = true

    private var mapAdapter: RefillPointsMapAdapter? = null

    fun setMapAdapter(mapAdapter: RefillPointsMapAdapter) {
        this.mapAdapter = mapAdapter
    }

    fun showRefillPointForMarker(marker: Marker) {
        val mapAdapter = mapAdapter ?: return
        mapAdapter.selectMarker(marker)
        mapAdapter.pointForMarker(marker)?.let {
            view.get()?.showPointInfo(it)
        }
    }

}