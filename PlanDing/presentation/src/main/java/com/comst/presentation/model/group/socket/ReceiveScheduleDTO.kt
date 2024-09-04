package com.comst.presentation.model.group.socket

import com.comst.domain.model.base.Schedule
import com.comst.domain.model.base.ScheduleType
import com.comst.domain.util.DateUtils
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ReceiveScheduleDTO(
    val scheduleCommonResponse: ScheduleDTO,
    val type: String,
    val action: String
)

data class ScheduleDTO(
    val id: Long,
    val title: String,
    val content: String,
    val scheduleDate: String,
    val startTime: Int,
    val endTime: Int,
    val groupName: String
)

fun ReceiveScheduleDTO.toDomainModel(): Schedule {
    val localDate =  DateUtils.uiDateToLocalDate(scheduleCommonResponse.scheduleDate, "yyyy-MM-dd")
    return Schedule(
        scheduleId = scheduleCommonResponse.id,
        title = scheduleCommonResponse.title,
        content = scheduleCommonResponse.content,
        startTime = scheduleCommonResponse.startTime,
        endTime = scheduleCommonResponse.endTime,
        day = DateUtils.getDayOfWeekUIFormat(localDate),
        complete = false,
        groupName = scheduleCommonResponse.groupName,
    )
}