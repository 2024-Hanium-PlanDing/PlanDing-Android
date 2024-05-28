package com.comst.domain.util

import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale


object DateUtils {

    fun getCurrentDate(format: String = "yyyy-MM-dd"): String {
        val formatter = DateTimeFormatter.ofPattern(format, Locale.getDefault())
        return LocalDate.now().format(formatter)
    }

    fun getCurrentDateTime(format: String = "yyyy-MM-dd HH:mm:ss"): String {
        val formatter = DateTimeFormatter.ofPattern(format, Locale.getDefault())
        return LocalDateTime.now().format(formatter)
    }

    fun uiDateToLocalDate(dateString: String, format: String = "yyyy년 MM월 dd일"): LocalDate {
        val formatter = DateTimeFormatter.ofPattern(format, Locale.getDefault())
        return LocalDate.parse(dateString, formatter)
    }

    fun localDateToUIDate(date: LocalDate, format: String = "yyyy년 MM월 dd일"): String {
        val formatter = DateTimeFormatter.ofPattern(format, Locale.getDefault())
        return date.format(formatter)
    }

    fun uiDateTimeToLocalDateTime(dateTimeString: String, format: String = "yyyy년 MM월 dd일 HH:mm:ss"): LocalDateTime {
        val formatter = DateTimeFormatter.ofPattern(format, Locale.getDefault())
        return LocalDateTime.parse(dateTimeString, formatter)
    }

    fun localDateTimeToUIDateTime(dateTime: LocalDateTime, format: String = "yyyy년 MM월 dd일 HH:mm:ss"): String {
        val formatter = DateTimeFormatter.ofPattern(format, Locale.getDefault())
        return dateTime.format(formatter)
    }

    fun localDateToDate(localDate: LocalDate): Date {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant())
    }

    fun dateToLocalDate(date: Date): LocalDate {
        return Instant.ofEpochMilli(date.time).atZone(ZoneId.systemDefault()).toLocalDate()
    }

    fun localDateTimeToDate(localDateTime: LocalDateTime): Date {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant())
    }

    fun dateToLocalDateTime(date: Date): LocalDateTime {
        return Instant.ofEpochMilli(date.time).atZone(ZoneId.systemDefault()).toLocalDateTime()
    }

    fun dateToUIDate(date: Date, format: String = "yyyy년 MM월 dd일"): String {
        val localDate = dateToLocalDate(date)
        return localDateToUIDate(localDate, format)
    }

    fun uiDateToDate(dateString: String, format: String = "yyyy년 MM월 dd일"): Date {
        val localDate = uiDateToLocalDate(dateString, format)
        return localDateToDate(localDate)
    }

    fun getDayOfWeek(date: LocalDate, format: String = "EEEE"): String {
        val formatter = DateTimeFormatter.ofPattern(format, Locale.getDefault())
        return date.format(formatter)
    }

    fun getDayOfWeek(date: Date, format: String = "EEEE"): String {
        val localDate = dateToLocalDate(date)
        val formatter = DateTimeFormatter.ofPattern(format, Locale.getDefault())
        return localDate.format(formatter)
    }


    fun getCurrentDayOfWeek(format: String = "EEEE"): String {
        return getDayOfWeek(LocalDate.now(), format)
    }
}