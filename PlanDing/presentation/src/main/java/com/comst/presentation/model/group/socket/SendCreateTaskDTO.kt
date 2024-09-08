package com.comst.presentation.model.group.socket

data class SendCreateTaskDTO(
    val title: String,
    val content: String,
    val deadline: String,
    val status: String,
    val managerCode: String,
    val userCodes: List<String>,
    val scheduleId: Long
)