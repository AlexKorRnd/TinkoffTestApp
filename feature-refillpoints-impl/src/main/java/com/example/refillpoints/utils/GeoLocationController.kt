package com.example.refillpoints.utils

import android.Manifest
import android.content.Context
import androidx.annotation.RequiresPermission
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest

class GeoLocationController (
    context: Context,
    private val locationCallback: LocationCallback
) {

    private val fusedLocationProviderClient: FusedLocationProviderClient =
        FusedLocationProviderClient(context)

    @RequiresPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    fun startLocationObservation(intervalInMiliseconds: Long) {
        val locationRequest = LocationRequest().apply {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            interval = intervalInMiliseconds
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null)
    }


    fun stopLocationObservation() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }
}