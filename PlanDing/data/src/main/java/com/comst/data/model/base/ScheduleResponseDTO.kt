package com.comst.data.model.base

import com.comst.domain.model.base.Schedule
import com.comst.domain.util.DateUtils

data class ScheduleResponseDTO(
    val id : Long,
    val title : String,
    val content : String,
    val scheduleDate : String,
    val startTime : Int,
    val endTime : Int,
    val groupName : String,
    val groupCode: String,
    val groupThumbnail: String
)
fun ScheduleResponseDTO.toDomainModel(): Schedule {
    val localDate = DateUtils.uiDateToLocalDate(scheduleDate,"yyyy-MM-dd")
    return Schedule(
        scheduleId = id,
        title = title,
        content = content,
        startTime = startTime,
        endTime = endTime,
        day = DateUtils.getDayOfWeekUIFormat(localDate),
        complete = false,
        groupName = groupName,
    )
}