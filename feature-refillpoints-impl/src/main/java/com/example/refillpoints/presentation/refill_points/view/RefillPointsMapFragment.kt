package com.example.refillpoints.presentation.refill_points.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.refillpoints.BuildConfig
import com.example.refillpoints.R
import com.example.refillpoints.domain.models.LocationModel
import com.example.refillpoints.domain.models.RefillPointModel
import com.example.refillpoints.presentation.Router
import com.example.refillpoints.presentation.refill_points.presenter.RefillPointsMapPresenter
import com.example.refillpoints.presentation.refill_points.presenter.RefillPointsPresenter
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.content_bottom_sheet.*


class RefillPointsMapFragment : Fragment(), OnMapReadyCallback,
    RefillPointsPageView, RefillPointsMapView {

    companion object {
        fun newInstance(): RefillPointsMapFragment =
            RefillPointsMapFragment()

        private const val BUNDLE_MAP_VIEW = "${BuildConfig.APPLICATION_ID}.args.BUNDLE_MAP_VIEW"

        private const val DEFAULT_ZOOM = 14F
    }

    private val parentPresenter: RefillPointsPresenter?
        get() = (activity as? RefillPointsActivity)?.presenter

    private lateinit var presenter: RefillPointsMapPresenter

    private var map: GoogleMap? = null
    private lateinit var bottomSheetDelegate: BottomSheetDelegate

    private var mapView: MapView? = null

    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)

        presenter = RefillPointsMapPresenter(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mapView = view.findViewById(R.id.mapView)
        mapView?.onCreate(savedInstanceState?.getBundle(BUNDLE_MAP_VIEW))

        mapView?.getMapAsync(this)
        setupBottomSheet()
    }

    override fun onStart() {
        super.onStart()
        mapView?.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapView?.onResume()
    }

    override fun onPause() {
        mapView?.onPause()
        super.onPause()
    }

    override fun onStop() {
        mapView?.onStop()
        super.onStop()
    }

    override fun onDestroy() {
        mapView?.onDestroy()
        super.onDestroy()
    }

    override fun onLowMemory() {
        mapView?.onLowMemory()
        super.onLowMemory()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        mapView?.onSaveInstanceState(outState)
        super.onSaveInstanceState(outState)
    }

    private fun setupBottomSheet() {
        bottomSheetDelegate = BottomSheetDelegate(vgBottomSheet) { refillPoint ->
            refillPoint ?: return@BottomSheetDelegate
            val context = context ?: return@BottomSheetDelegate
            Router.openDetailedScreen(context, refillPoint)
        }
    }

    override fun onMapReady(map: GoogleMap) {
        this.map = map

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
        presenter.myLocation?.let { locationModel ->
            moveCamera(locationModel)
        }
        map.setOnMarkerClickListener { marker ->
            presenter.showRefillPointInfoById(marker.tag as? String)
            true
        }
        map.uiSettings.isMyLocationButtonEnabled = true
        map.uiSettings.isZoomControlsEnabled = true
        map.setOnMyLocationButtonClickListener(object: GoogleMap.OnMyLocationButtonClickListener {
            override fun onMyLocationButtonClick(): Boolean {
                (activity as? RefillPointsActivity)?.findMyLocation()
                return true
            }
        })

    }

    private fun LatLng.toLocation(): LocationModel = LocationModel(this.latitude, this.longitude)

    override fun showRefillPoints(points: List<RefillPointModel>) {
        val map = map ?: return
        map.clear()
        presenter.saveRefillPoints(points)
        points.forEach { point ->
            map.addMarker(
                MarkerOptions()
                    .position(LatLng(point.location.latitude, point.location.longitude))
                    .title(point.partner.name)
            ).apply {
                tag = point.externalId
            }
        }
    }

    override fun showPointInfo(refillPointModel: RefillPointModel) {
        bottomSheetDelegate.bindAndOpen(refillPointModel)
        //Toast.makeText(requireContext(), "${refillPointModel.fullAddress}, ${refillPointModel.partner.name}", Toast.LENGTH_LONG).show()
    }

    @SuppressLint("MissingPermission")
    override fun showMyLocation(locationModel: LocationModel, isDefault: Boolean) {
        presenter.myLocation = locationModel
        presenter.isDefault = isDefault
        if (isDefault.not()) {
            map?.isMyLocationEnabled = true
        }
        moveCamera(locationModel)
    }

    private fun moveCamera(locationModel: LocationModel) {
        map?.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(locationModel.latitude, locationModel.longitude), DEFAULT_ZOOM))
    }
}