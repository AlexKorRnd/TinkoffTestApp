package com.example.refillpoints.di

import com.example.core.di.PerScreen
import com.example.refillpoints.presentation.presenter.RefillPointsPresenter
import dagger.Subcomponent

@Subcomponent
@PerScreen
interface RefillPointsScreenComponent {

    fun refillPointsPresenter(): RefillPointsPresenter

}