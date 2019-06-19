package com.example.refillpoints.presentation.refill_points.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.core.base.extensions.openAppSettings
import com.example.core.utils.permissions.AppPermissionController
import com.example.core.utils.permissions.PermissionController
import com.example.refillpoints.R
import com.example.refillpoints.di.RefillPointsFeatureComponent
import com.example.refillpoints.domain.models.LocationModel
import com.example.refillpoints.domain.models.RefillPointModel
import com.example.refillpoints.presentation.refill_points.presenter.RefillPointsPresenter
import com.example.refillpoints.utils.GeoLocationController
import com.google.android.gms.location.LocationAvailability
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import kotlinx.android.synthetic.main.activity_refill_points.*
import kotlinx.android.synthetic.main.content_toolbar.*
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class RefillPointsActivity: AppCompatActivity(), RefillPointsView {

    companion object {
        // TODO: 20.06.19 moved it to repository
        private val DEFAULT_LOCATION = LocationModel(55.751999, 37.617734)

        private const val REQUEST_CODE_LOCATION_PERMISSION = 11
        private val LOCATION_UPDATES_TIME = TimeUnit.SECONDS.toMillis(5)
    }

    @Inject
    lateinit var presenter: RefillPointsPresenter

    private lateinit var pagerAdapter: RefillPointsPagerAdapter

    private lateinit var geoLocationController: GeoLocationController

    private val geoLocationCallback = object : LocationCallback() {
        override fun onLocationResult(result: LocationResult) {
            super.onLocationResult(result)
            Timber.i("result:: latitude = ${result.lastLocation?.latitude}, longitude = ${result.lastLocation?.longitude}")
            geoLocationController.stopLocationObservation()
            result.lastLocation?.let { location ->
                showMyLocation(LocationModel(location.latitude, location.longitude), false)
            }
        }

        override fun onLocationAvailability(locationAvailability: LocationAvailability) {
            super.onLocationAvailability(locationAvailability)
            // TODO: 20.06.19 process errors
            if (locationAvailability.isLocationAvailable.not()) {
                showMyLocation(DEFAULT_LOCATION, true)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        RefillPointsFeatureComponent.get().inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_refill_points)
        presenter.setView(this)
        setupToolbar()
        setTitle(R.string.refill_points_screen_title)
        setupPager()

        geoLocationController = GeoLocationController(this, geoLocationCallback)
        findMyLocation()
    }

    fun findMyLocation() {
        if (AppPermissionController.checkGrantedLocationPermission(this)) {
            geoLocationController.startLocationObservation(LOCATION_UPDATES_TIME)
        } else {
            AppPermissionController.requestLocationPermission(this, REQUEST_CODE_LOCATION_PERMISSION)
        }
    }

    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == REQUEST_CODE_LOCATION_PERMISSION) {
            val result = AppPermissionController.processRequestPermissionsResult(this, permissions, grantResults)
            when(result) {
                PermissionController.PermissionResult.GRANTED -> {
                    geoLocationController.startLocationObservation(LOCATION_UPDATES_TIME)
                }
                PermissionController.PermissionResult.DENIED -> {
                    showMyLocation(DEFAULT_LOCATION, true)
                }
                PermissionController.PermissionResult.NEVER_ASK_AGAIN -> {
                    showMyLocation(DEFAULT_LOCATION, true)
                    showExplanationForEnablePermissionInSettings()
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun showExplanationForEnablePermissionInSettings() {
        AlertDialog.Builder(this)
            .setTitle(R.string.location_permission_need_enable_in_settings_title)
            .setMessage(R.string.location_permission_need_enable_in_settings_message)
            .setPositiveButton(R.string.location_permission_need_enable_in_settings_button_positive) { dialog, which ->
                openAppSettings(this)
            }
            .setNegativeButton(android.R.string.cancel, null)
            .show()
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
    }

    private fun setupPager() {
        pagerAdapter = RefillPointsPagerAdapter(this, supportFragmentManager)
        viewPager.adapter = pagerAdapter
        tabLayout.setupWithViewPager(viewPager)
    }

    private fun showMyLocation(location: LocationModel, isDefault: Boolean) {
        (pagerAdapter.getRegisteredFragment(0) as? RefillPointsMapView)?.showMyLocation(location, isDefault)
    }

    override fun showRefillPoints(points: List<RefillPointModel>) {
        for (i in 0 until pagerAdapter.count) {
            (pagerAdapter.getRegisteredFragment(i) as? RefillPointsPageView)?.showRefillPoints(points)
        }
    }

    override fun showError(error: Throwable) {
        Toast.makeText(this, error.message, Toast.LENGTH_LONG).show()
    }
}