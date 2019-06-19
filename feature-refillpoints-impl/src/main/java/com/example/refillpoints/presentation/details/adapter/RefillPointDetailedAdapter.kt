package com.example.refillpoints.presentation.details.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.core.base.BaseAdapter
import com.example.core.base.extensions.onClickDebounce
import com.example.refillpoints.presentation.refill_points.list.adapter.ItemClickListener

class RefillPointDetailedAdapter(
        private val callback: ItemClickListener
): BaseAdapter<DetailedAdapterItem, RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_SIMPLE_ITEM = 1
        private const val TYPE_CLICKABLE_ITEM = 2
    }

    override fun getItemViewType(position: Int): Int {
        val item = items[position]
        return when(item) {
            is SimpleItem -> TYPE_SIMPLE_ITEM
            is ClickableItem -> TYPE_CLICKABLE_ITEM
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            TYPE_SIMPLE_ITEM -> SimpleItemHolder.create(parent)
            TYPE_CLICKABLE_ITEM -> ClickableItemHolder.create(parent).apply {
                itemView.onClickDebounce {
                    callback(adapterPosition)
                }
            }
            else -> throw IllegalArgumentException("found not defined viewType $viewType")
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        when(holder) {
            is SimpleItemHolder -> holder.bind(item as SimpleItem)
            is ClickableItemHolder -> holder.bind(item as ClickableItem)
        }
    }
}