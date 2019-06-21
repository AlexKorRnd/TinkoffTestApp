package com.example.refillpoints.presentation

import android.app.Activity
import android.app.ActivityOptions
import android.os.Build
import android.view.View
import com.example.refillpoints.R
import com.example.refillpoints.domain.models.RefillPointModel
import com.example.refillpoints.presentation.details.view.RefillPointDetailInfoActivity

//very simple router
object Router {

    fun openDetailedScreen(activity: Activity, sharedView: View?, data: RefillPointModel) {
        val intent = RefillPointDetailInfoActivity.createIntent(activity, data)
        val transitionName = activity.getString(R.string.partner_icon_transition_name)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val options = ActivityOptions.makeSceneTransitionAnimation(
                activity,
                sharedView,
                transitionName
            )
            activity.startActivity(intent, options.toBundle())
        } else {
            activity.startActivity(intent)
        }
    }

}