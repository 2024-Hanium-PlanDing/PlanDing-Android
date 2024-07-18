package com.comst.presentation.model.group.socket

data class SendDeleteScheduleDTO(
    val groupCode: String,
    val scheduleId: Long,
    val userCode: String
)
