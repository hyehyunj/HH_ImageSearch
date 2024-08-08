package com.android.hh_imagesearch.activity.data.model

import java.util.UUID

data class ContentModel(
    val uId: String = UUID.randomUUID().toString(),
    var selectedContent: Boolean = false,
    val contentsType: Int = 0,
    val thumbnail: String = "",
    val title: String = "",
    val dateTime: String = "",
    val type : String
)

//이미지 결과를 SearchModel타입으로 변환시켜주는 함수
fun imageToContentModel(content: MutableList<ImageDocuments>): List<ContentModel> = with(content) {
    return map { content ->
        ContentModel(
            thumbnail = content.thumbnailUrl,
            title = content.displaySiteName,
            dateTime = content.datetime,
            type = "image"
        )
    }
}

//동영상 결과를 변환시켜주는 함수
fun videoToContentModel(content : MutableList<VideoDocuments>) : List<ContentModel> = with(content) {
    return map {content ->
        ContentModel(thumbnail = content.thumbnail, title = content.title, dateTime = content.dateTime, type = "video")
    }
}