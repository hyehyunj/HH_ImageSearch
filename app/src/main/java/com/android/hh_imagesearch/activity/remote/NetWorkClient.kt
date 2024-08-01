package com.android.hh_imagesearch.activity.remote

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetWorkClient {
    private const val IMAGE_BASE_URL = "https://dapi.kakao.com"

    val apiService: NetWorkInterface
        get() = instance.create(NetWorkInterface::class.java)//인스턴스화

    // Retrofit 인스턴스를 초기화하고 반환한다.
    private val instance: Retrofit
        get() {
            // Gson 객체 생성. setLenient()는 JSON 파싱이 좀 더 유연하게 처리되도록 한다.
            val gson = GsonBuilder().setLenient().create()

            // Retrofit 빌더를 사용하여 Retrofit 인스턴스 생성
            return Retrofit.Builder()
                .baseUrl(IMAGE_BASE_URL)  // 기본 URL 설정
                .addConverterFactory(GsonConverterFactory.create(gson))  // JSON 파싱을 위한 컨버터 추가
                .build()
        }
}

