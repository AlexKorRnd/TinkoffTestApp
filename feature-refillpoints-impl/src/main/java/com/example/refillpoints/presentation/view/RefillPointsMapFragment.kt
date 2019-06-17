package com.example.refillpoints.presentation.view

import android.os.Bundle
import android.util.Log
import com.example.refillpoints.data.network.responses.Location
import com.example.refillpoints.data.network.responses.RefillPointsResponse
import com.example.refillpoints.presentation.presenter.RefillPointsPresenter
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions



class RefillPointsMapFragment: SupportMapFragment(), OnMapReadyCallback,
    RefillPointsPageView {

    companion object {
        fun newInstance(): RefillPointsMapFragment =
                RefillPointsMapFragment()
    }

    private val parentPresenter: RefillPointsPresenter?
        get() = (activity as? RefillPointsActivity)?.presenter

    //private lateinit var presenter: RefillPointsMapPresenter

    private var map: GoogleMap? = null

    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        //presenter = RefillPointsMapPresenter()

        getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap) {
        this.map = map
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(RefillPointsActivity.TEST_LOCATION, 12F))
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
    }

    private fun LatLng.toLocation(): Location = Location(this.latitude, this.longitude)

    override fun showRefillPoints(points: List<RefillPointsResponse>) {
        val map = map ?: return
        map.clear()
        Log.i("test________", "points.size = ${points.size}")
        points.forEach {
            map.addMarker(
                MarkerOptions()
                    .position(LatLng(it.location.latitude, it.location.longitude))
                    .title(it.partnerName)
            )
            Log.i("test________", "point.address = ${it.fullAddress}")
        }
    }
}