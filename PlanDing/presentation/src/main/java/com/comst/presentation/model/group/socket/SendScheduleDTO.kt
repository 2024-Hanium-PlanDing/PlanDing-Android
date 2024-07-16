package com.comst.presentation.model.group.socket

data class SendScheduleDTO(
    val groupCode: String,
    val scheduleId: Long?,
    val userCode: String,
    val title: String?,
    val content: String?,
    val scheduleDate: String,
    val startTime: Int?,
    val endTime: Int?,
    val action: String
)

enum class Action(val description: String) {
    CREATE("CREATE"),
    UPDATE("UPDATE"),
    DELETE("DELETE")
}