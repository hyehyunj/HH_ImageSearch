package com.android.hh_imagesearch.activity.data.model

import com.google.gson.annotations.SerializedName


data class ImageModel(
    val meta: Meta,
    val imageDocuments: MutableList<ImageDocuments>
)

data class Meta(
    val total_count: Int,
    val pageable_count: Int,
    val is_end: Boolean
)

data class ImageDocuments(
//    val collection: String,
    @SerializedName("thumbnail_url")
    val thumbnail_url: String,
//    val image_url: String,
//    val width: Int,
//    val height: Int,
    @SerializedName("display_sitename")
    val display_sitename: String,
//    val doc_url: String,
    @SerializedName("datetime")
    val datetime: String
)




