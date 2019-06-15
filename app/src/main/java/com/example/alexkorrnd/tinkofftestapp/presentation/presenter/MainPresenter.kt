package com.example.alexkorrnd.tinkofftestapp.presentation.presenter

import com.example.alexkorrnd.tinkofftestapp.routing.GlobalNavigator
import javax.inject.Inject


class MainPresenter @Inject constructor(
    val navigator: GlobalNavigator
) {

    fun showRefillPoints() {
        navigator.showRefillPoints()
    }
}