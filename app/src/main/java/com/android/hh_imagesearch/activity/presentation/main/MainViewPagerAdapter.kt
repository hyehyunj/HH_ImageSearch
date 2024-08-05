package com.android.hh_imagesearch.activity.presentation.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.android.hh_imagesearch.activity.presentation.home.HomeFragment
import com.android.hh_imagesearch.activity.presentation.my.MyFragment

class MainViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
//    private lateinit var mainViewPagerAdapter: MainViewPagerAdapter
    private val fragments = listOf(HomeFragment(), MyFragment())

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}