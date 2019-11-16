package com.android.example.meuruidometro.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.android.example.meuruidometro.fragments.InfoListFragment
import com.android.example.meuruidometro.fragments.NoiseMeterFragment

class CustomPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> NoiseMeterFragment()
            else -> InfoListFragment()
        }
    }

    override fun getCount(): Int {
        return 2
    }
}