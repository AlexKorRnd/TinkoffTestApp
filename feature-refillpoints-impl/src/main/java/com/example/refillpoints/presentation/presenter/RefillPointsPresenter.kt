package com.example.refillpoints.presentation.presenter

import android.annotation.SuppressLint
import android.util.Log
import com.example.refillpoints.data.network.responses.Location
import com.example.refillpoints.domain.RefillPointsInteractor
import com.example.refillpoints.presentation.view.RefillPointsView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.lang.ref.WeakReference
import javax.inject.Inject

class RefillPointsPresenter @Inject constructor(
    private val refillPointsInteractor: RefillPointsInteractor
) {

    private var view: WeakReference<RefillPointsView>? = null

    @SuppressLint("CheckResult")
    fun loadRefillPoints(lat: Double, lng: Double, radius: Int) {
        refillPointsInteractor.loadRefillPoints(lat, lng, radius)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                view?.get()?.showRefillPoints(result)
            }, { error ->
                view?.get()?.showError(error)
            })
    }

    @SuppressLint("CheckResult")
    fun loadRefillPoints(lat: Double, lng: Double, topLeft: Location, topRight: Location, bottomRight: Location, bottomLeft: Location) {
        refillPointsInteractor.calculateRadius(topLeft, topRight, bottomRight, bottomLeft)
            .subscribeOn(Schedulers.io())
            .flatMap { radius ->
                Log.d("test___", "radius = $radius")
                refillPointsInteractor.loadRefillPoints(lat, lng, radius)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                view?.get()?.showRefillPoints(result)
            }, { error ->
                view?.get()?.showError(error)
            })
    }

    fun setView(view: RefillPointsView) {
        this.view = WeakReference(view)
    }
}