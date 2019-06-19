package com.example.refillpoints.start

import android.content.Context
import android.content.Intent
import com.example.core.di.PerFeature
import com.example.feature_refillpoints_api.RefillPointsStarter
import com.example.refillpoints.presentation.refill_points.view.RefillPointsActivity
import javax.inject.Inject

@PerFeature
class RefillPointsStarterImpl @Inject constructor(): RefillPointsStarter {

    override fun start(context: Context) {
        context.startActivity(Intent(context, RefillPointsActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        })
    }
}