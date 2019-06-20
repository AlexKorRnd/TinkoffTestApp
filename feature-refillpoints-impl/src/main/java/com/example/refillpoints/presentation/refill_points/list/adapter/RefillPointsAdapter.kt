package com.example.refillpoints.presentation.refill_points.list.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.core.base.BaseAdapter
import com.example.core.base.extensions.onClickDebounce
import com.example.refillpoints.domain.models.RefillPointModel

typealias ItemClickListener = (position: Int) -> Unit

class RefillPointsAdapter(
    private val callback: ItemClickListener
): BaseAdapter<RefillPointModel, RefillPointHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RefillPointHolder =
            RefillPointHolder.create(parent).apply {
                itemView.onClickDebounce {
                    callback(adapterPosition)
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