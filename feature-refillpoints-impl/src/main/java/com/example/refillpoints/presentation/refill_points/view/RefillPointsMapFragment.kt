package com.example.refillpoints.presentation.refill_points.view

import android.os.Bundle
import android.widget.Toast
import com.example.refillpoints.data.network.responses.Location
import com.example.refillpoints.domain.models.RefillPointModel
import com.example.refillpoints.presentation.refill_points.presenter.RefillPointsMapPresenter
import com.example.refillpoints.presentation.refill_points.presenter.RefillPointsPresenter
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class RefillPointsMapFragment : SupportMapFragment(), OnMapReadyCallback,
    RefillPointsPageView, RefillPointsMapView {

    companion object {
        fun newInstance(): RefillPointsMapFragment =
            RefillPointsMapFragment()
    }

    private val parentPresenter: RefillPointsPresenter?
        get() = (activity as? RefillPointsActivity)?.presenter

    private lateinit var presenter: RefillPointsMapPresenter

    private var map: GoogleMap? = null

    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)

        presenter = RefillPointsMapPresenter(this)

        getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap) {
        this.map = map
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(RefillPointsActivity.TEST_LOCATION, 14F))
        map.setOnCameraIdleListener {
            val visibleRegion = map.projection.visibleRegion
            parentPresenter?.loadRefillPoints(
                lat = visibleRegion.latLngBounds.center.latitude,
                lng = visibleRegion.latLngBounds.center.longitude,
                topLeft = visibleRegion.farLeft.toLocation(),
                topRight = visibleRegion.farRight.toLocation(),
                bottomLeft = visibleRegion.nearLeft.toLocation(),
                bottomRight = visibleRegion.nearRight.toLocation()
            )
        }
        map.setOnMarkerClickListener { marker ->
            presenter.showRefillPointInfoById(marker.tag as? String)
            true
        }
    }

    private fun LatLng.toLocation(): Location = Location(this.latitude, this.longitude)

    override fun showRefillPoints(points: List<RefillPointModel>) {
        val map = map ?: return
        map.clear()
        presenter.saveRefillPoints(points)
        points.forEach { point ->
            map.addMarker(
                MarkerOptions()
                    .position(LatLng(point.location.latitude, point.location.longitude))
                    .title(point.partner.name)
                    //.draggable(false)
            ).apply {
                tag = point.externalId
            }
        }
    }

    override fun showPointInfo(refillPointModel: RefillPointModel) {
        Toast.makeText(requireContext(), "${refillPointModel.fullAddress}, ${refillPointModel.partner.name}", Toast.LENGTH_LONG).show()
    }
}