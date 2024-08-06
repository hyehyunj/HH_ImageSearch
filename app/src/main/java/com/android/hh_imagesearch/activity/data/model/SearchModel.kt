package com.android.hh_imagesearch.activity.data.model

import java.util.UUID

data class SearchModel(
    val uId: String = UUID.randomUUID().toString(),
    val selectedContent: Boolean = false,
    val contentsType: Int = 0,
    val thumbnail: String = "",
    val siteName: String = "",
    val dateTime: String = "",
    val type : String
)

//이미지 결과를 SearchModel타입으로 변환시켜주는 함수
fun imageToSearchModel(content: MutableList<ImageDocuments>): List<SearchModel> = with(content) {
    return map { content ->
        SearchModel(
            thumbnail = content.thumbnailUrl,
            siteName = content.displaySiteName,
            dateTime = content.datetime,
            type = "image"
        )
    }
}

//동영상 결과를 변환시켜주는 함수
fun videoToSearchModel(content : MutableList<VideoDocuments>) : List<SearchModel> = with(content) {
    return map {content ->
        SearchModel(thumbnail = content.thumbnail, dateTime = content.dateTime, type = "video")
    }
}