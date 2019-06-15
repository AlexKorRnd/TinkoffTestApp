package com.example.alexkorrnd.tinkofftestapp.di.app



import com.example.alexkorrnd.tinkofftestapp.presentation.presenter.MainPresenter
import com.example.core.di.PerScreen
import dagger.Subcomponent

@Subcomponent
@PerScreen
interface MainScreenComponent {

    fun mainPresenter(): MainPresenter
}