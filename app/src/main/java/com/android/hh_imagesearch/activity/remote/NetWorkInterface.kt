package com.android.hh_imagesearch.activity.remote

import com.android.hh_imagesearch.activity.data.ImageModel
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface NetWorkInterface {
    @Headers("Authorization: KakaoAK 8db9d5916e062bc33491d4ee1708b47d")
    @GET("v2/search/image")
    suspend fun getImage(
        @Query("query") query: String
    ): ImageModel
}