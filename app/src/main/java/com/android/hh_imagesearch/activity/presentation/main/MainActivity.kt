package com.android.hh_imagesearch.activity.presentation.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.android.hh_imagesearch.activity.data.model.ContentModel
import com.android.hh_imagesearch.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainActivity : AppCompatActivity(), SelectedInterface {

    private lateinit var binding: ActivityMainBinding
    private var addData = mutableListOf<ContentModel>()
    private lateinit var mainViewModel : MainViewModel
    companion object {
        private const val TAG = "MainActivity"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //뷰모델 초기화
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        //레이아웃 초기화
        initLayout()

        //최근검색어 백업
        loadSearchWord()
        mainViewModel.backUpContent(loadMyImage())

        //최근검색어 저장
        mainViewModel.searchWordLiveData.observe(this) {
            saveSearchWord(it.toString())
        }

        mainViewModel.selectedLiveData.observe(this) {
            if(mainViewModel.selectedLiveData.value != null)
            saveMyImage(it)
        }


    }

    //레이아웃 초기화 함수 : 뷰페이저, 탭레이아웃 연결
    private fun initLayout() {
        val viewPager = binding.mainViewPager
        val mainViewPagerAdapter = MainViewPagerAdapter(this)
        viewPager.adapter = mainViewPagerAdapter

        TabLayoutMediator(binding.mainTab, binding.mainViewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "이미지"
                1 -> {
                    tab.text = "보관함"
                }
            }
        }.attach()
    }

    private fun saveSearchWord(searchWord: String) {
        val pref = getSharedPreferences("pref", 0)
        val edit = pref.edit()
        edit.putString("search_word", searchWord)
        edit.apply()
    }

    private fun loadSearchWord() {
        val pref = getSharedPreferences("pref", 0)
        mainViewModel.updateSearchWord(pref.getString("search_word", "") ?: "")
    }






    private fun saveMyImage(image: List<ContentModel>) {
        val pref = getSharedPreferences("pref", 0)
        val edit = pref.edit()
        val jsonString = Gson().toJson(image)
        edit.putString("search_image", jsonString)
        edit.apply()
    }

    private fun loadMyImage() : MutableList<ContentModel> {
        val pref = getSharedPreferences("pref", 0)
        val jsonString = pref.getString("search_image", "")
        return if (jsonString != "") {
            val type = object : TypeToken<MutableList<ContentModel>>() {}.type
            Gson().fromJson(jsonString, type)
        } else {
            mutableListOf()
        }
    }

    override fun select(item: ContentModel) {
        mainViewModel.addContent(item)
    }


}