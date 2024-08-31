package com.comst.domain.util

import com.comst.domain.model.base.DaysOfWeek
import com.comst.domain.model.base.SchedulePeriodModel
import java.time.DayOfWeek
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

    fun getDayOfWeekUIFormat(date: LocalDate): String{
        val dayOfWeekShort = date.format(DateTimeFormatter.ofPattern("E", Locale.KOREAN))
        return "$dayOfWeekShort(${date.dayOfMonth})"
    }


    fun getCurrentDayOfWeek(format: String = "EEEE"): String {
        return getDayOfWeek(LocalDate.now(), format)
    }

    fun getWeekDays(date: LocalDate): List<String> {
        val daysOfWeek = DaysOfWeek.values().map { it.korea }
        val result = mutableListOf<String>()
        val startOfWeek = if (date.dayOfWeek == DayOfWeek.SUNDAY) {
            date
        } else {
            date.with(DayOfWeek.SUNDAY).minusDays(7)
        }
        for (i in 0..6) {
            val currentDay = startOfWeek.plusDays(i.toLong())
            result.add("${daysOfWeek[i]}(${currentDay.dayOfMonth})")
        }
        return result
    }

    fun getWeekDays(date: Date): List<String> {
        return getWeekDays(dateToLocalDate(date))
    }

    fun getDayStartAndEnd(date: LocalDate, format: String = "yyyy-MM-dd"): SchedulePeriodModel {
        val formatter = DateTimeFormatter.ofPattern(format, Locale.getDefault())
        val formattedDate = date.format(formatter)
        return SchedulePeriodModel(formattedDate, formattedDate)
    }

    fun getDayStartAndEnd(date: Date, format: String = "yyyy-MM-dd"): SchedulePeriodModel {
        return getDayStartAndEnd(dateToLocalDate(date), format)
    }


    fun getWeekStartAndEnd(date: LocalDate, format: String = "yyyy-MM-dd"): SchedulePeriodModel {
        val formatter = DateTimeFormatter.ofPattern(format, Locale.getDefault())
        val startOfWeek = date.minusDays(date.dayOfWeek.value.toLong() % 7)
        val endOfWeek = startOfWeek.plusDays(6)
        return SchedulePeriodModel(startOfWeek.format(formatter), endOfWeek.format(formatter))
    }

    fun getWeekStartAndEnd(date: Date, format: String = "yyyy-MM-dd"): SchedulePeriodModel {
        return getWeekStartAndEnd(dateToLocalDate(date), format)
    }

    fun getServerFormat(date: LocalDate, format: String = "yyyy-MM-dd"): String {
        val formatter = DateTimeFormatter.ofPattern(format, Locale.getDefault())
        return date.format(formatter)
    }

    fun getServerFormat(date: Date, format: String = "yyyy-MM-dd"): String {
        return getServerFormat(dateToLocalDate(date), format)
    }

    fun getDateFromWeekdayIndex(date: LocalDate, index: Int): LocalDate {
        val dayOfWeek = date.dayOfWeek.value % 7
        val startOfWeek = date.minusDays(dayOfWeek.toLong())
        return startOfWeek.plusDays(index.toLong())
    }

    fun getDateFromWeekdayIndex(date: Date, index: Int): LocalDate {
        return getDateFromWeekdayIndex(dateToLocalDate(date), index)
    }

    fun getTimeFromDateTimeString(dateTimeString: String): String {
        val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
        val dateTime = LocalDateTime.parse(dateTimeString, formatter)
        val outputFormatter = DateTimeFormatter.ofPattern("HH:mm")
        return dateTime.format(outputFormatter)
    }

    fun formatDateAndTime(date: String?, time: String?): String {
        val formattedDate = date?.let {
            LocalDate.parse(it, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                .format(DateTimeFormatter.ofPattern("yyyy년 M월 d일"))
        } ?: "날짜"

        val formattedTime = time?.let {
            "${it.padStart(2, '0')}:00"
        } ?: "시간"

        return "$formattedDate $formattedTime"
    }

    fun formatDateTime(input: String): String {
        val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        val dateTime = LocalDateTime.parse(input, inputFormatter)
        val outputFormatter = DateTimeFormatter.ofPattern("yyyy년 M월 d일 H시", Locale.getDefault())
        return dateTime.format(outputFormatter)
    }

}