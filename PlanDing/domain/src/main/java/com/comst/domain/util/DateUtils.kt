package com.comst.domain.util

import java.text.SimpleDateFormat
import java.util.Date

object DateUtils {

    fun getCurrentDate(format: String = "yyyy-MM-dd"): String {
        val dateFormat = SimpleDateFormat(format)
        return dateFormat.format(Date())
    }
}