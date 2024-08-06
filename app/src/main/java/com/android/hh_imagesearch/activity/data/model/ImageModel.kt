package com.android.hh_imagesearch.activity.data.model

import com.google.gson.annotations.SerializedName


data class ImageModel(
    @SerializedName("meta")
    val imageMeta: ImageMeta,
    @SerializedName("documents")
    val imageDocuments: MutableList<ImageDocuments>
)

data class ImageMeta(
    @SerializedName("total_count")
    val totalCount: Int,
    @SerializedName("pageable_count")
    val pageableCount: Int,
    @SerializedName("is_end")
    val isEnd: Boolean
)

data class ImageDocuments(
//    val collection: String,
    @SerializedName("thumbnail_url")
    val thumbnailUrl: String,
//    val image_url: String,
//    val width: Int,
//    val height: Int,
    @SerializedName("display_sitename")
    val displaySiteName: String,
//    val doc_url: String,
    @SerializedName("datetime")
    val datetime: String
)




