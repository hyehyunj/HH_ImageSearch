package com.android.hh_imagesearch.activity.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.hh_imagesearch.activity.data.model.SearchModel

class MainViewModel : ViewModel() {
    private val _imagesLiveData = MutableLiveData<List<SearchModel>>()
    val imagesLiveData: LiveData<List<SearchModel>> = _imagesLiveData

    val selectedImageList = _imagesLiveData.value?.toMutableList() ?: mutableListOf()
    private val deletedImageList = mutableListOf<SearchModel>()

    private val _searchWordLiveData = MutableLiveData<String>()
    val searchWordLiveData: LiveData<String> = _searchWordLiveData



    //클릭된 이미지 추가하는 함수
    fun addImage(image: SearchModel) {
        selectedImageList.add(image)
        _imagesLiveData.value = selectedImageList
    }

    //클릭된 이미지 삭제하는 함수
    fun removeImage(image: SearchModel) {
        selectedImageList.remove(image)
        deletedImageList.add(image)
        _imagesLiveData.value = selectedImageList
    }

    //선택한 이미지 복원하는 함수
    fun backUpImage(image : MutableList<SearchModel>) {
        selectedImageList.clear()
        selectedImageList.addAll(image)
        _imagesLiveData.value = selectedImageList
    }
    //검색어
    fun updateSearchWord(search : String) {
        _searchWordLiveData.value = search
    }

}