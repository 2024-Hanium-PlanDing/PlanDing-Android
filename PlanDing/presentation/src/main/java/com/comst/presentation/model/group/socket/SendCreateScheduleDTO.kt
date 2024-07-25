package com.comst.presentation.model.group.socket

data class SendCreateScheduleDTO(
    val userCode: String,
    val title: String,
    val content: String,
    val scheduleDate: String,
    val startTime: Int,
    val endTime: Int
)