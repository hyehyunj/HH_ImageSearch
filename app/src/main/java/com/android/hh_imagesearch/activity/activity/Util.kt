package com.android.hh_imagesearch.activity.activity

import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class Util {

    companion object{

        private var util: Util? = null

        @Synchronized
        fun getInstance() : Util{
            if(util == null) {
                util = Util()
            }
            return util as Util
        }
    }

}

fun makeDateFormat(date: String): String {

    val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREAN)

    return date.format(DateTimeFormatter.ofPattern("yyyy년 M월 dd일"))
}