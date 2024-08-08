package com.android.hh_imagesearch.activity.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.hh_imagesearch.activity.data.model.ContentModel
import com.android.hh_imagesearch.activity.data.model.ImageMeta

class MainViewModel : ViewModel() {

    //검색결과
    private val _searchResultLiveData = MutableLiveData<MutableList<ContentModel>?>()
    val searchResultLiveData: LiveData<MutableList<ContentModel>?> = _searchResultLiveData

    //검색어
    private val _searchWordLiveData = MutableLiveData<String>()
    val searchWordLiveData: LiveData<String> = _searchWordLiveData

    //검색필터
    private val _searchFilterLiveData = MutableLiveData<String>("전체")
    val searchFilterLiveData: LiveData<String> = _searchFilterLiveData

    //보관함.마이컨텐츠
    private val _myContentLiveData = MutableLiveData<MutableList<ContentModel>>()
    val myContentLiveData: LiveData<MutableList<ContentModel>> = _myContentLiveData
    private val myContentList = _myContentLiveData.value ?: mutableListOf()

    //검색결과
    fun receivedSearchResult(content: MutableList<ContentModel>) {
        _searchResultLiveData.value = content
    }

    //전체 검색결과
    fun receivedMergedSearchResult(
        image: MutableList<ContentModel>,
        video: MutableList<ContentModel>
    ) {
        _searchResultLiveData.value = image
        _searchResultLiveData.value?.addAll(video)
    }

    //검색어
    fun updateSearchWord(search: String) {
        _searchWordLiveData.value = search
    }

    //필터
    fun updateFilter(filter: String) {
        _searchFilterLiveData.value = filter
    }

    //클릭된 컨텐츠 보관함에 추가하는 함수
    fun addContent(content: ContentModel) {
        myContentList.add(content)
        _myContentLiveData.value = myContentList
    }

    //저장된 컨텐츠 보관함에서 삭제하는 함수
    fun removeContent(content: ContentModel) {
        _searchResultLiveData.value?.find { it.thumbnail == content.thumbnail }?.selectedContent =
                false
        myContentList.remove(content)
        _myContentLiveData.value = myContentList
    }

    //모든 저장된 컨텐츠 보관함에서 삭제하는 함수
    fun removeAllContent() {
        myContentList.forEach { i ->
            _searchResultLiveData.value?.find { it.thumbnail == i.thumbnail }?.selectedContent =
                false
        }
        myContentList.clear()
        _myContentLiveData.value = myContentList
    }

    //저장된 컨텐츠 복원하는 함수
    fun backUpContent(content: MutableList<ContentModel>) {
        myContentList.clear()
        myContentList.addAll(content)
        _myContentLiveData.value = myContentList
    }

    //저장된 컨텐츠 표시 반영하는 함수
    fun findMyContent() {
        myContentList.forEach { i ->
            _searchResultLiveData.value?.find { it.thumbnail == i.thumbnail }?.selectedContent =
                true
        }
        _searchResultLiveData.value?.forEach { i ->
            myContentList.find { it.thumbnail == i.thumbnail }?.selectedContent =
                true
        }
    }
}