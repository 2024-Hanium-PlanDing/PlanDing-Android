package com.comst.presentation.model.group.socket

import com.comst.domain.model.base.Schedule
import com.comst.domain.model.base.ScheduleType
import com.comst.domain.util.DateUtils
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ReceiveScheduleDTO(
    val content: String? = null,
    val endTime: Int? = null,
    val groupName: String? = null,
    val id: Long,
    val scheduleDate: String? = null,
    val startTime: Int? = null,
    val title: String? = null,
    val type: String? = null,
    val action: String
)


fun ReceiveScheduleDTO.toDomainModel(): Schedule {
    val localDate = scheduleDate?.let { DateUtils.uiDateToLocalDate(it, "yyyy-MM-dd") }
    return Schedule(
        scheduleId = id,
        title = title.orEmpty(),
        content = content.orEmpty(),
        startTime = startTime ?: 0,
        endTime = endTime ?: 0,
        day = localDate?.let { DateUtils.getDayOfWeekUIFormat(it) }.orEmpty(),
        complete = false,
        groupName = groupName.orEmpty(),
        type = ScheduleType.GROUP
    )
}