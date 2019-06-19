package com.example.refillpoints.start

import android.app.Activity
import android.content.Intent
import com.example.core.di.PerFeature
import com.example.feature_refillpoints_api.RefillPointsStarter
import com.example.refillpoints.presentation.refill_points.view.RefillPointsActivity
import javax.inject.Inject

@PerFeature
class RefillPointsStarterImpl @Inject constructor(): RefillPointsStarter {

    override fun start(activity: Activity) {
        activity.startActivity(Intent(activity, RefillPointsActivity::class.java))
    }
}