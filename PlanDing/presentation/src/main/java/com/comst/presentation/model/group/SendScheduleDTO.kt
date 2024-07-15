package com.comst.presentation.model.group

import com.comst.domain.model.base.Schedule
import com.comst.domain.model.base.ScheduleType

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

fun SendScheduleDTO.toDomainModel(): Schedule{
    return Schedule(
        scheduleId = scheduleId ?: -1,
        title = title ?: "",
        content = content ?: "",
        startTime = startTime ?: -1,
        endTime = endTime ?: -1,
        day = scheduleDate,
        complete = false,
        groupName = "",
        type = ScheduleType.GROUP
    )
}

enum class Action(val description: String) {
    CREATE("CREATE"),
    UPDATE("UPDATE"),
    DELETE("DELETE")
}