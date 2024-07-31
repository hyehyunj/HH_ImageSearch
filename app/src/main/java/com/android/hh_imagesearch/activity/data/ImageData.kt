package com.android.hh_imagesearch.activity.data

import android.icu.text.ListFormatter.Width
import android.view.Display
import com.google.gson.annotations.SerializedName
import org.w3c.dom.Document
import java.time.LocalDateTime

data class ImageData(val response: ImageData)

data class ImageResponse(
    @SerializedName("meta")
    val meta: Meta,
    @SerializedName("documents")
    val document: Document
)

data class Meta(
    val total_count: Int,
    val pageable_count: Int,
    val is_end: Boolean
)

data class document(
    val cllection: String,
    val thumbnail_url: String,
    val image_url: String,
    val width: Int,
    val height: Int,
    val display_sitename: String,
    val doc_url: String,
    val datetime: LocalDateTime
)


)

