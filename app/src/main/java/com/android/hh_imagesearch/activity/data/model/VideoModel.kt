package com.android.hh_imagesearch.activity.data.model

import com.google.gson.annotations.SerializedName


data class VideoModel(
    @SerializedName("meta")
    val videoMeta: VideoMeta,
    @SerializedName("documents")
    val videoDocuments: MutableList<VideoDocuments>
)

data class VideoMeta(
    @SerializedName("total_count")
    val totalCount: Int,
    @SerializedName("pageable_count")
    val pageableCount: Int,
    @SerializedName("is_end")
    val isEnd: Boolean
)

data class VideoDocuments(
//    val collection: String,
    @SerializedName("url")
    val url: String,
//    val image_url: String,
//    val width: Int,
//    val height: Int,
    @SerializedName("thumbnail")
    val thumbnail: String,
//    val doc_url: String,
    @SerializedName("datetime")
    val dateTime: String
)




