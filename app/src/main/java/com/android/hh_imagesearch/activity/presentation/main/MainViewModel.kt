package com.android.hh_imagesearch.activity.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.hh_imagesearch.activity.data.model.ContentModel

class MainViewModel : ViewModel() {

    //검색결과
    private val _searchLiveData = MutableLiveData<MutableList<ContentModel>>()
    val searchLiveData: LiveData<MutableList<ContentModel>> = _searchLiveData

    //선택된 컨텐츠
    private val _selectedLiveData = MutableLiveData<MutableList<ContentModel>>()
    val selectedLiveData: LiveData<MutableList<ContentModel>> = _selectedLiveData
    val selectedList = _selectedLiveData.value ?: mutableListOf()

    //검색어
    private val _searchWordLiveData = MutableLiveData<String>()
    val searchWordLiveData: LiveData<String> = _searchWordLiveData

    //검색필터
    private val _searchFilterLiveData = MutableLiveData<String>("전체")
    val searchFilterLiveData: LiveData<String> = _searchFilterLiveData






    //검색결과
    fun updateContentModel(content : MutableList<ContentModel>) {
        _searchLiveData.value = content
    }
    //전체 검색결과
    fun updateMixContentModel(image : MutableList<ContentModel>, video : MutableList<ContentModel>) {
        _searchLiveData.value = image
        _searchLiveData.value?.addAll(video)
    }

    //검색어
    fun updateSearchWord(search : String) {
        _searchWordLiveData.value = search
    }

    //필터
    fun updateFilter(filter : String) {
        _searchFilterLiveData.value = filter
    }

    //클릭된 컨텐츠 추가하는 함수
    fun addContent(content: ContentModel) {
//        _selectedLiveData.value.add(content)
        selectedList.add(content)
        _selectedLiveData.value = selectedList
    }
    //클릭된 이미지 삭제하는 함수
    fun removeContent(image: ContentModel) {
        selectedList.remove(image)
        _selectedLiveData.value = selectedList
    }
    //선택한 이미지 복원하는 함수
    fun backUpContent(image : MutableList<ContentModel>) {
        selectedList.clear()
        selectedList.addAll(image)
        _selectedLiveData.value = selectedList
    }





}