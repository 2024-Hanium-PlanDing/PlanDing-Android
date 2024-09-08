package com.comst.data.model.groupSchedule

import com.comst.domain.model.groupSchedule.GroupScheduleResponseModel

data class GroupScheduleResponseDTO(
    val id: Long,
    val title: String,
    val content: String,
    val scheduleDate: String,
    val startTime: Int,
    val endTime: Int,
    val day: String,
    val type: String,
    val groupName: String,
    val userScheduleAttendances: List<GroupScheduleParticipantResponseDTO>,
)

fun GroupScheduleResponseDTO.toDomainModel(): GroupScheduleResponseModel {
    return GroupScheduleResponseModel(
        id = id,
        title = title,
        content = content,
        scheduleDate = scheduleDate,
        startTime = startTime,
        endTime = endTime,
        day = day,
        type = type,
        groupName = groupName,
        userScheduleAttendances = userScheduleAttendances.map { it.toDomainModel() }
    )
}