package com.comst.domain.model.base

data class ScheduleModel(
    val title : String,
    val content : String,
    val scheduleDate : String,
    val startTime : Int,
    val endTime : Int,
)
