package com.android.hh_imagesearch.activity.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.android.hh_imagesearch.activity.adapter.ViewPagerAdapter
import com.android.hh_imagesearch.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //레이아웃 연결
        setLayout()


    }


    private fun setLayout() {
        val viewPager = binding.mainViewPager
        val viewPagerAdapter = ViewPagerAdapter(this)
        viewPager.adapter = viewPagerAdapter

    }


}