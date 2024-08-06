package com.android.hh_imagesearch.activity.data.model


data class VideoModel(
    val videoMeta: Meta,
    val videoDocuments: MutableList<VideoDocuments>
)

data class VideoMeta(
    val total_count: Int,
    val pageable_count: Int,
    val is_end: Boolean
)


data class VideoDocuments(
//    val collection: String,
    val url: String,
//    val image_url: String,
//    val width: Int,
//    val height: Int,
    val thumbnail: String,
//    val doc_url: String,
    val datetime: String
)




