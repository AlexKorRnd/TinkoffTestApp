package com.example.refillpoints.presentation.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.refillpoints.R
import com.example.refillpoints.data.network.responses.RefillPointsResponse
import com.example.refillpoints.di.RefillPointsFeatureComponent
import com.example.refillpoints.presentation.presenter.RefillPointsPresenter
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.activity_refill_points.*
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

        presenter.setView(this)

        setupPager()

        presenter.loadRefillPoints(TEST_LOCATION.latitude, TEST_LOCATION.longitude, 1000)
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

    override fun showRefillPoints(points: List<RefillPointsResponse>) {
        for (i in 0 until pagerAdapter.count) {
            (pagerAdapter.getRegisteredFragment(i) as? RefillPointsPageView)?.showRefillPoints(points)
        }
    }

    override fun showError(error: Throwable) {

    }
}