package com.android.hh_imagesearch.activity.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.android.hh_imagesearch.activity.fragment.ImageSearchFragment
import com.android.hh_imagesearch.activity.fragment.MyStorageFragment

class ViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    val fragments = listOf(ImageSearchFragment(), MyStorageFragment())

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}