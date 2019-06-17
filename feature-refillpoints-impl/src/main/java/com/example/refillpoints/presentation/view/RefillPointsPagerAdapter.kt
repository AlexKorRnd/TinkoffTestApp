package com.example.refillpoints.presentation.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.core.base.SmartFragmentStatePagerAdapter

class RefillPointsPagerAdapter(
    fragmentManager: FragmentManager
): SmartFragmentStatePagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        return when(position) {
            0 -> RefillPointsMapFragment.newInstance()
            else -> Fragment()
        }
    }

    override fun getCount(): Int {
        return 1
    }
}