package com.example.refillpoints.presentation.details.view

import com.example.refillpoints.presentation.details.adapter.DetailedAdapterItem

interface RefillPointDetailedView {

    fun showItems(items: List<DetailedAdapterItem>)

}