package com.android.hh_imagesearch.activity.data.model

import java.util.UUID

data class SearchModel(
    val uId: String = UUID.randomUUID().toString(),
    val selectedContent: Boolean = false,
    val thumbnail: String = "",
    val siteName: String = "",
    val dateTime: String = ""

)

//이미지 결과를 SearchModel타입으로 변환시켜주는 함수
fun imageToSearchResult(content: MutableList<Documents>): List<SearchModel> = with(content) {
    return map { content ->
        SearchModel(
            thumbnail = content.thumbnail_url,
            siteName = content.display_sitename,
            dateTime = content.datetime
        )
    }
}


//동영상 결과를 변환시켜주는 함수
//fun videoToSearchResult(content : MutableList<VideoDocuments>) : MutableList<SearchResult> = with(content) {
//    return map {content ->
//        SearchResult(thumbnail = content.thumbnail ?: "" )
//    }
//}