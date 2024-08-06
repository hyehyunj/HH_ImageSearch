package com.android.hh_imagesearch.activity.data.remote

import com.android.hh_imagesearch.activity.data.model.ImageModel
import com.android.hh_imagesearch.activity.data.model.VideoModel
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface NetWorkInterface {

    //카카오 이미지 검색결과 받아오는 코루틴 전용 메소드
    @Headers("Authorization: KakaoAK 8db9d5916e062bc33491d4ee1708b47d")
    @GET("v2/search/image")
    suspend fun searchImage(
        @Query("query") query: String
    ): ImageModel

    //카카오 동영상 검색결과 받아오는 코루틴 전용 메소드
    @Headers("Authorization: KakaoAK 8db9d5916e062bc33491d4ee1708b47d")
    @GET("v2/search/vclip")
    suspend fun searchVideo(
        @Query("query") query: String
    ): VideoModel


}