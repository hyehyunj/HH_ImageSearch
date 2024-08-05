package com.android.hh_imagesearch.activity.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.hh_imagesearch.activity.data.model.Documents

class MainViewModel : ViewModel() {
    private val _imagesLiveData = MutableLiveData<List<Documents>>()
    val imagesLiveData: LiveData<List<Documents>> = _imagesLiveData
    private val currentList = _imagesLiveData.value?.toMutableList() ?: mutableListOf()

    private val _searchWordLiveData = MutableLiveData<String>()
    val searchWordLiveData: LiveData<String> = _searchWordLiveData



    //클릭된 이미지 추가하는 함수
    fun addImage(image: Documents) {
        currentList.add(image)
        _imagesLiveData.value = currentList
    }

    //클릭된 이미지 삭제하는 함수
    fun removeImage(image: Documents) {
        currentList.remove(image)
        _imagesLiveData.value = currentList
    }

    //검색어
    fun updateSearchWord(search : String) {
        _searchWordLiveData.value = search
    }

}