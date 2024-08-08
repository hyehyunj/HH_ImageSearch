package com.android.hh_imagesearch.activity.presentation.util

import java.sql.Timestamp
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

object Util {

fun makeDateTimeFormat(
    timestamp: String?,
    fromFormat: String = "yyyy-MM-dd'T'HH:mm:ss.SSS+09:00",
    toFormat: String = "yyyy-MM-dd HH:mm:ss"
): String {
    var date: Date? = null
    var result = ""
    try {
        val format = SimpleDateFormat(fromFormat)
        date = format.parse(timestamp)
    } catch (e: ParseException) {
        e.printStackTrace()
    }

    val dateFormat = SimpleDateFormat(toFormat)
    result = dateFormat.format(date)
    return result}

}