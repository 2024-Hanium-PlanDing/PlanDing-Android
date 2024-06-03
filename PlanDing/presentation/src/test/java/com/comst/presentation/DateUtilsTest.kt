package com.comst.presentation

import com.comst.domain.util.DateUtils
import org.junit.Assert
import org.junit.Test
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date

class DateUtilsTest {

    val localDate: LocalDate = LocalDate.now()
    val uiDate: String = localDate.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일"))
    val date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant())

    @Test
    fun 로컬데이트_to_UI데이트_테스트(){
        val actualLocalDate = DateUtils.localDateToUIDate(localDate)
        Assert.assertEquals(uiDate, actualLocalDate)
    }

    @Test
    fun UI데이트_to_로컬데이트_테스트(){
        val actualUIDate = DateUtils.uiDateToLocalDate(uiDate)
        Assert.assertEquals(localDate, actualUIDate)
    }

    @Test
    fun 데이트_to_UI데이트() {
        val actualUIDate = DateUtils.dateToUIDate(date)
        Assert.assertEquals(uiDate, actualUIDate)
    }

    @Test
    fun UI데이트_to_데이트() {
        val actualDate = DateUtils.uiDateToDate(uiDate)
        Assert.assertEquals(date, actualDate)
    }
}