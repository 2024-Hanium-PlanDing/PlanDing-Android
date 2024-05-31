package com.comst.presentation.model

data class ScheduleEvent(
    val day: String,
    val startTime: Int,
    val endTime: Int,
    val title: String
)