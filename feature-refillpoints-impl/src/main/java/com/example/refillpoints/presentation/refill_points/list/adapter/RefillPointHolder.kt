package com.example.refillpoints.presentation.refill_points.list.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.core.base.extensions.screenDensityName
import com.example.core.utils.ConstructPictureUrl
import com.example.core.utils.ImageLoader
import com.example.refillpoints.R
import com.example.refillpoints.domain.models.PartnerModel
import com.example.refillpoints.domain.models.RefillPointModel

class RefillPointHolder(view: View): RecyclerView.ViewHolder(view) {

    private val ivIcon: ImageView = ViewCompat.requireViewById(view, R.id.ivIcon)
    private val tvTitle: TextView = ViewCompat.requireViewById(view, R.id.tvTitle)
    private val tvText: TextView = ViewCompat.requireViewById(view, R.id.tvText)
    private val tvSeen: TextView = ViewCompat.requireViewById(view, R.id.tvSeen)

    fun bind(item: RefillPointModel) {
        ImageLoader.load(imageView = ivIcon, url = ConstructPictureUrl.construct(PartnerModel.PICTURE_URL_PREFIX, itemView.context.screenDensityName(), item.partner.picture),
            isCircle = true)
        tvTitle.text = item.partner.name
        tvText.text = item.fullAddress
        if (item.isSeen) {
            tvSeen.visibility = View.VISIBLE
        } else {
            tvSeen.visibility = View.GONE
        }
    }

    companion object {
        fun create(parent: ViewGroup): RefillPointHolder =
                RefillPointHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_refill_point, parent, false))
    }
}