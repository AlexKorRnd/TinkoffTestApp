package com.example.refillpoints.presentation.refill_points.view

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.core.base.SmartFragmentStatePagerAdapter
import com.example.refillpoints.R
import com.example.refillpoints.presentation.refill_points.list.view.RefillPointsListFragment

class RefillPointsPagerAdapter(
    private val context: Context,
    fragmentManager: FragmentManager
): SmartFragmentStatePagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        return when(position) {
            0 -> RefillPointsMapFragment.newInstance()
            else -> RefillPointsListFragment.newInstance()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position) {
            0 -> context.getString(R.string.refill_points_screen_title_page_map_title)
            1 -> context.getString(R.string.refill_points_screen_title_page_list_title)
            else -> ""
        }
    }

    override fun getCount(): Int {
        return 2
    }
}