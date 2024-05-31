package com.comst.data.model.commonSchedule

import com.comst.domain.model.base.ScheduleEvent
import com.comst.domain.model.base.ScheduleType
import com.comst.domain.util.DateUtils

data class ScheduleResponseDTO(
    val id : Long,
    val title : String,
    val content : String,
    val scheduleDate : String,
    val startTime : Int,
    val endTime : Int,
    val complete : Boolean,
    val groupName : String,
    val type : String
)

fun ScheduleResponseDTO.toDomainModel(): ScheduleEvent {
    val localDate = DateUtils.uiDateToLocalDate(scheduleDate,"yyyy-MM-dd")
    return ScheduleEvent(
        scheduleId = id,
        title = title,
        content = content,
        startTime = startTime,
        endTime = endTime,
        day = DateUtils.getDayOfWeekUIFormat(localDate),
        complete = complete,
        groupName = if (type == ScheduleType.GROUP.type) groupName else null,
        type = when(type){
            ScheduleType.GROUP.type -> ScheduleType.GROUP
            ScheduleType.PERSONAL.type -> ScheduleType.PERSONAL
            else -> throw IllegalArgumentException("Unknown schedule type: $type")
        }
    )
}