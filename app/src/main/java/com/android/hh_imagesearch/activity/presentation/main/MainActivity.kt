package com.android.hh_imagesearch.activity.presentation.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.hh_imagesearch.activity.data.model.Documents
import com.android.hh_imagesearch.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity(){

    private lateinit var binding: ActivityMainBinding
private var addData = mutableListOf<Documents>()
    companion object {
        private const val TAG = "MainActivity"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //레이아웃 연결
        initLayout()






    }

    //레이아웃 초기화 함수 : 뷰페이저, 탭레이아웃 연결
    private fun initLayout() {
        val viewPager = binding.mainViewPager
        val mainViewPagerAdapter = MainViewPagerAdapter(this)
        viewPager.adapter = mainViewPagerAdapter

        TabLayoutMediator(binding.mainTab, binding.mainViewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "이미지"
                1 -> {tab.text = "보관함"
                }
            }
        }.attach()
            }



    //인터페이스 : 검색결과 페이지에서 데이터 받기
//override fun onDataReceived(data: Documents) {
//    addData += data
//
//    Log.d(TAG, "$addData")
//}

    //검색결과 보관함에 전달
//private fun addStorage() {
////val fragment = MyStorageFragment.newInstance(addData)
//}


}