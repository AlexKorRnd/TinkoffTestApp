package com.example.refillpoints.presentation.details.presenter

import android.annotation.SuppressLint
import com.example.refillpoints.R
import com.example.refillpoints.domain.models.RefillPointModel
import com.example.refillpoints.presentation.details.adapter.ClickableItem
import com.example.refillpoints.presentation.details.adapter.ClickableType
import com.example.refillpoints.presentation.details.adapter.DetailedAdapterItem
import com.example.refillpoints.presentation.details.adapter.SimpleItem
import com.example.refillpoints.presentation.details.view.RefillPointDetailedView
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.lang.ref.WeakReference

class RefillPointDetailedInfoPresenter(
        view: RefillPointDetailedView
) {

    private val view = WeakReference(view)

    @SuppressLint("CheckResult")
    fun processData(data: RefillPointModel) {
        Single.fromCallable {
            val result = mutableListOf<DetailedAdapterItem>()

            result.add(SimpleItem(R.string.refill_points_screen_detailed_enrollment, data.partner.depositionDuration, null))
            result.add(SimpleItem(R.string.refill_points_screen_detailed_limits, data.partner.limitations, null))
            result.add(SimpleItem(R.string.refill_points_screen_detailed_conditions, data.partner.description, null))
            data.workHours?.let {
                result.add(SimpleItem(R.string.refill_points_screen_detailed_work_hours, data.workHours, null))
            }

            data.partner.url?.let {
                result.add(ClickableItem(R.string.refill_points_screen_detailed_website, data.partner.url, R.drawable.ic_language_24dp, ClickableType.WEBSITE))
            }
            data.phones?.let {
                result.add(ClickableItem(R.string.refill_points_screen_detailed_phone, data.phones, R.drawable.ic_phone_24dp, ClickableType.PHONE))
            }

            result.toList()
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    view.get()?.showItems(it)
                }, {})
    }

}