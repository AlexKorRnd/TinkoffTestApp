package com.example.refillpoints.presentation.refill_points.list.adapter

import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.core.base.BaseAdapter
import com.example.core.base.extensions.onClickDebounce
import com.example.refillpoints.domain.models.RefillPointModel



class RefillPointsAdapter(
    private val callback: Callback
): BaseAdapter<RefillPointModel, RefillPointHolder>() {

    interface Callback {
        fun onItemClick(position: Int, ivIcon: ImageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RefillPointHolder =
            RefillPointHolder.create(parent).apply {
                itemView.onClickDebounce {
                    callback.onItemClick(adapterPosition, this.ivIcon)
                }
            }

    override fun onBindViewHolder(holder: RefillPointHolder, position: Int) {
        holder.bind(items[position])
    }

    fun updateItem(item: RefillPointModel) {
        val index = items.indexOfFirst { it.externalId == item.externalId }
        if (index != RecyclerView.NO_POSITION) {
            replace(item, index)
        }
    }
}