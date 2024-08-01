package com.android.hh_imagesearch.activity.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.android.hh_imagesearch.activity.adapter.ViewPagerAdapter
import com.android.hh_imagesearch.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //레이아웃 연결
        initLayout()





    }


    private fun initLayout() {
        val viewPager = binding.mainViewPager
        val viewPagerAdapter = ViewPagerAdapter(this)
        viewPager.adapter = viewPagerAdapter

        TabLayoutMediator(binding.mainTab, binding.mainViewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "이미지"
                1 -> tab.text = "보관함"
            }
        }.attach()



    }




private fun initFragment(fragment: Fragment) {

}


}