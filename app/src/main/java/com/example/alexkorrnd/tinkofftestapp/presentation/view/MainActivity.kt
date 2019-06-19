package com.example.alexkorrnd.tinkofftestapp.presentation.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.alexkorrnd.tinkofftestapp.di.app.AppComponent
import com.example.alexkorrnd.tinkofftestapp.presentation.presenter.MainPresenter

class MainActivity: AppCompatActivity() {

    lateinit var presenter: MainPresenter

    private fun providePresenter(): MainPresenter {
        return AppComponent.get()
            .mainScreenComponent()
            .mainPresenter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        presenter = providePresenter()
        presenter.showRefillPoints(this)
        finish()
        super.onCreate(savedInstanceState)
    }

}