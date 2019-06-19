package com.example.refillpoints.presentation

import android.content.Context
import com.example.refillpoints.domain.models.RefillPointModel
import com.example.refillpoints.presentation.details.view.RefillPointDetailInfoActivity

//very simple router
object Router {

    fun openDetailedScreen(context: Context, data: RefillPointModel) {
        context.startActivity(RefillPointDetailInfoActivity.createIntent(context, data))
    }

}