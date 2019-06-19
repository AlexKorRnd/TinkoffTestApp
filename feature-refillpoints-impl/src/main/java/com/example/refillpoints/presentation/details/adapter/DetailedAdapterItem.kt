package com.example.refillpoints.presentation.details.adapter

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

sealed class DetailedAdapterItem

data class SimpleItem(
        @StringRes
        val subTitle: Int,
        val text: String?,
        @DrawableRes
        val iconResId: Int?
): DetailedAdapterItem()

data class ClickableItem(
        @StringRes
        val subTitle: Int,
        val text: String?,
        @DrawableRes
        val iconResId: Int?,
        val clickableType: ClickableType
): DetailedAdapterItem()

enum class ClickableType{
    PHONE,
    WEBSITE
}