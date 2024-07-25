package com.comst.presentation.model.group.socket

data class SendUpdateScheduleDTO (
    val scheduleId: Long,
    val userCode: String,
    val title: String,
    val content: String,
    val scheduleDate: String,
    val startTime: Int,
    val endTime: Int
)