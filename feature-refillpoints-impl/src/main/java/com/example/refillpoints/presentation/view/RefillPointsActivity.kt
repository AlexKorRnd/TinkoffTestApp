package com.example.refillpoints.presentation.view

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.refillpoints.BuildConfig
import com.example.refillpoints.R
import com.example.refillpoints.di.RefillPointsFeatureComponent
import com.example.refillpoints.domain.models.RefillPointModel
import com.example.refillpoints.presentation.presenter.RefillPointsPresenter
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.activity_refill_points.*
import timber.log.Timber
import javax.inject.Inject

class RefillPointsActivity: AppCompatActivity(), RefillPointsView {

    companion object {
        val TEST_LOCATION = LatLng(47.287185, 39.743355)
    }

    @Inject
    lateinit var presenter: RefillPointsPresenter

    private lateinit var pagerAdapter: RefillPointsPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        RefillPointsFeatureComponent.get().inject(this)
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_refill_points)
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        presenter.setView(this)

        setupPager()
    }

    override fun onPause() {
        if (isFinishing) {
            RefillPointsFeatureComponent.get().resetComponent()
        }
        super.onPause()
    }

    private fun setupPager() {
        pagerAdapter = RefillPointsPagerAdapter(supportFragmentManager)
        viewPager.adapter = pagerAdapter
        tabLayout.setupWithViewPager(viewPager)
    }

    override fun showRefillPoints(points: List<RefillPointModel>) {
        for (i in 0 until pagerAdapter.count) {
            (pagerAdapter.getRegisteredFragment(i) as? RefillPointsPageView)?.showRefillPoints(points)
        }
    }

    override fun showError(error: Throwable) {

    }
}