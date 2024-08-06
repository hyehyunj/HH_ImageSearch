package com.android.hh_imagesearch.activity.data.remote

import com.android.hh_imagesearch.activity.data.model.ImageModel
import com.android.hh_imagesearch.activity.data.model.VideoModel
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface VideoNetWorkInterface {
    @Headers("Authorization: KakaoAK 8db9d5916e062bc33491d4ee1708b47d")
    @GET("v2/search/vclip")
    suspend fun searchVideo(
        @Query("query") query: String
    ): VideoModel
}