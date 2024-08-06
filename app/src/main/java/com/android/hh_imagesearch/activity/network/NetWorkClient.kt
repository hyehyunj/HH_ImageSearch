package com.android.hh_imagesearch.activity.network

import com.android.hh_imagesearch.activity.data.remote.NetWorkInterface
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetWorkClient {
    private const val BASE_URL = "https://dapi.kakao.com"

    val apiService: NetWorkInterface
        get() = instance.create(NetWorkInterface::class.java)//인스턴스화

    // Retrofit 인스턴스
    private val instance: Retrofit
        get() {
            val gson = GsonBuilder().setLenient().create()
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        }
}

