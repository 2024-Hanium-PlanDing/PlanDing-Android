package com.comst.presentation.model.group.socket

data class SendUpdateScheduleDTO (
    val groupCode: String,
    val scheduleId: Long,
    val userCode: String,
    val title: String,
    val content: String,
    val scheduleDate: String,
    val startTime: Int,
    val endTime: Int
)