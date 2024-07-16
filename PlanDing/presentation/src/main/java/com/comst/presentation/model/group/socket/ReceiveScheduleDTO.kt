package com.comst.presentation.model.group.socket

import com.comst.domain.model.base.Schedule
import com.comst.domain.model.base.ScheduleType
import com.comst.domain.util.DateUtils
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ReceiveScheduleDTO(
    val content: String,
    val endTime: Int,
    val groupName: String,
    val id: Long,
    val scheduleDate: String,
    val startTime: Int,
    val title: String,
    val type: String
)


fun ReceiveScheduleDTO.toDomainModel(): Schedule {
    val localDate = DateUtils.uiDateToLocalDate(scheduleDate,"yyyy-MM-dd")
    return Schedule(
        scheduleId = id,
        title = title,
        content = content,
        startTime = startTime,
        endTime = endTime,
        day = DateUtils.getDayOfWeekUIFormat(localDate),
        complete = false,
        groupName = "",
        type = ScheduleType.GROUP
    )
}