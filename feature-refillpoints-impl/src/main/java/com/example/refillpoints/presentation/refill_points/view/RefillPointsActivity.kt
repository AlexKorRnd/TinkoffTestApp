package com.example.refillpoints.presentation.refill_points.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.refillpoints.R
import com.example.refillpoints.di.RefillPointsFeatureComponent
import com.example.refillpoints.domain.models.RefillPointModel
import com.example.refillpoints.presentation.refill_points.presenter.RefillPointsPresenter
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.activity_refill_points.*
import kotlinx.android.synthetic.main.content_toolbar.*
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
        setupToolbar()
        setTitle(R.string.refill_points_screen_title)
        setupPager()
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
    }

    private fun setupPager() {
        pagerAdapter = RefillPointsPagerAdapter(this, supportFragmentManager)
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