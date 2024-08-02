package com.android.hh_imagesearch.activity.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.hh_imagesearch.activity.data.model.Documents

class MainViewModel : ViewModel() {
    private val _imageLiveData = MutableLiveData<Documents>() //프라이빗한 실제 데이터
    val imageLiveData : LiveData<Documents> = _imageLiveData //읽기전용

    init {

    }

    fun setImage(selectedImage : Documents) {
        _imageLiveData.value = selectedImage
    }
}