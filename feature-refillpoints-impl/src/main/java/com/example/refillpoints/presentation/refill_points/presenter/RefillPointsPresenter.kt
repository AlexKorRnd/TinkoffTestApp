package com.example.refillpoints.presentation.refill_points.presenter

import android.annotation.SuppressLint
import com.example.core.base.RxBus
import com.example.refillpoints.data.network.responses.Location
import com.example.refillpoints.domain.RefillPointsInteractor
import com.example.refillpoints.presentation.refill_points.view.RefillPointsView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.lang.ref.WeakReference
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class RefillPointsPresenter @Inject constructor(
    private val refillPointsInteractor: RefillPointsInteractor
) {

    companion object {
        private const val CHANGED_LOCATION_DEBOUNCE_MS = 250L
    }

    private val rxBusLocations = RxBus<ChangedLocationEvent>()

    init {
        listeningChangedLocation()
    }

    @SuppressLint("CheckResult")
    fun listeningChangedLocation() {
        rxBusLocations.observeEvents(ChangedLocationEvent::class.java)
            .subscribeOn(Schedulers.io())
            .debounce(CHANGED_LOCATION_DEBOUNCE_MS, TimeUnit.MILLISECONDS)
            .subscribe({ event ->
                internalLoadRefillPoints(event.lat, event.lng, event.topLeft, event.topRight, event.bottomRight, event.bottomLeft)
            }, {

            })
    }

    @SuppressLint("CheckResult")
    private fun internalLoadRefillPoints(
        lat: Double, lng: Double, topLeft: Location, topRight: Location, bottomRight: Location,
        bottomLeft: Location
    ) {
        refillPointsInteractor.loadRefillPoints(lat, lng, topLeft, topRight, bottomRight, bottomLeft)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                view?.get()?.showRefillPoints(result)
            }, { error ->
                Timber.e(error)
                view?.get()?.showError(error)
            })
    }

    private var view: WeakReference<RefillPointsView>? = null

    @SuppressLint("CheckResult")
    fun loadRefillPoints(
        lat: Double, lng: Double, topLeft: Location, topRight: Location, bottomRight: Location,
        bottomLeft: Location
    ) {
        rxBusLocations.send(ChangedLocationEvent(lat, lng, topLeft, topRight, bottomRight, bottomLeft))
    }

    fun setView(view: RefillPointsView) {
        this.view = WeakReference(view)
    }

    private class ChangedLocationEvent(
        val lat: Double,
        val lng: Double,
        val topLeft: Location,
        val topRight: Location,
        val bottomRight: Location,
        val bottomLeft: Location
    )
}