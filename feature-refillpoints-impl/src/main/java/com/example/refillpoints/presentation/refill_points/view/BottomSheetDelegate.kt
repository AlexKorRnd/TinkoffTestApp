package com.example.refillpoints.presentation.refill_points.view

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.ViewCompat
import com.example.core.base.extensions.onClickDebounce
import com.example.core.base.extensions.screenDensityName
import com.example.core.utils.ConstructPictureUrl
import com.example.core.utils.ImageLoader
import com.example.refillpoints.R
import com.example.refillpoints.domain.models.PartnerModel
import com.example.refillpoints.domain.models.RefillPointModel
import com.google.android.material.bottomsheet.BottomSheetBehavior

class BottomSheetDelegate(
        rootView: ViewGroup,
        private val callback: (refillPoint: RefillPointModel?) -> Unit
) {

    private val holder = Holder(rootView)
    private val behaviour = BottomSheetBehavior.from(rootView)

    private var curItem: RefillPointModel? = null

    init {
        rootView.onClickDebounce {
            callback(curItem)
        }
    }

    fun bindAndOpen(item: RefillPointModel) {
        curItem = item
        behaviour.state = BottomSheetBehavior.STATE_EXPANDED
        with(holder) {
            ImageLoader.load(imageView = ivIcon, url = ConstructPictureUrl.construct(PartnerModel.PICTURE_URL_PREFIX, holder.view.context.screenDensityName(), item.partner.picture), isCircle = true)
            tvTitle.text = item.partner.name
            tvText.text = item.fullAddress
        }
    }

    class Holder(val view: View) {
        val ivIcon: ImageView = ViewCompat.requireViewById(view, R.id.ivIcon)
        val tvTitle: TextView = ViewCompat.requireViewById(view, R.id.tvTitle)
        val tvText: TextView = ViewCompat.requireViewById(view, R.id.tvText)
    }
}