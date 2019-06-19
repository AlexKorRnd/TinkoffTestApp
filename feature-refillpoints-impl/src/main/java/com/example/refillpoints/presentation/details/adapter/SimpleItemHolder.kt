package com.example.refillpoints.presentation.details.adapter

import android.content.Context
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.core.base.extensions.loadVector
import com.example.refillpoints.R

class SimpleItemHolder(
        view: View
): RecyclerView.ViewHolder(view) {

    private val tvSubTitle: TextView = ViewCompat.requireViewById(view, R.id.tvSubtitle)
    private val tvText: TextView = ViewCompat.requireViewById(view, R.id.tvText)
    private val ivIcon: ImageView = ViewCompat.requireViewById(view, R.id.ivIcon)

    private val context: Context
        get() = tvSubTitle.context

    fun bind(item: SimpleItem) {
        tvSubTitle.text = context.getString(item.subTitle)
        tvText.text = Html.fromHtml(item.text)
        if (item.iconResId == null) {
            ivIcon.visibility = View.INVISIBLE
        } else {
            ivIcon.visibility = View.VISIBLE
            ivIcon.setImageDrawable(item.iconResId.loadVector(context))
        }
    }

    companion object {
        fun create(parent: ViewGroup) =
                SimpleItemHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_refill_point_detail, parent, false))
    }

}