package com.example.refillpoints.presentation.refill_points.presenter

import com.example.refillpoints.domain.models.RefillPointModel
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class RefillPointsMapAdapter(
    val map: GoogleMap,
    private val curSelectedPointIcon: BitmapDescriptor,
    private val defaulPointIcon: BitmapDescriptor,
    private val seenPointIcon: BitmapDescriptor
) {

    private val markersPointsMap = mutableMapOf<Marker, RefillPointModel>()

    private var curSelectedMarker: Marker? = null

    val curSelectedPoint: RefillPointModel?
        get() = curSelectedMarker?.let { marker ->
            markersPointsMap[marker]
        }

    fun updateRefillPoints(refillPoints: List<RefillPointModel>) {
        map.clear()
        markersPointsMap.clear()
        refillPoints.forEach { point ->
            val marker = map.addMarker(
                MarkerOptions()
                    .position(LatLng(point.location.latitude, point.location.longitude))
                    .title(point.partner.name)

            ).apply {
                tag = point.externalId
                updateIconByModel(point)
            }
            markersPointsMap[marker] = point
        }
    }

    private fun Marker.updateIconByModel(point: RefillPointModel) {
        val icon = when {
            this.tag == curSelectedMarker?.tag -> {
                curSelectedPointIcon
            }
            point.isSeen -> {
                seenPointIcon
            }
            else -> {
                defaulPointIcon
            }
        }
        setIcon(icon)
    }

    fun selectMarker(marker: Marker) {
        val prevSelectedMarker = curSelectedMarker
        curSelectedMarker = marker
        marker.updateIconByModel(markersPointsMap[marker]!!)
        val prevSelectedPoint = markersPointsMap[prevSelectedMarker]
        prevSelectedPoint?.let {
            prevSelectedMarker?.updateIconByModel(prevSelectedPoint)
        }
    }

    fun updateMarker(refillPoint: RefillPointModel) {
        markersPointsMap.keys.find { it.tag == refillPoint.externalId }?.let { marker ->
            markersPointsMap[marker] = refillPoint
            marker.updateIconByModel(refillPoint)
        }
    }

    fun pointForMarker(marker: Marker): RefillPointModel? =
            markersPointsMap[marker]

}