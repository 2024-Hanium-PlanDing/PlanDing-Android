package com.comst.domain.model.groupSchedule

data class GroupScheduleResponseModel(
    val id: Long,
    val title: String,
    val content: String,
    val scheduleDate: String,
    val startTime: Int,
    val endTime: Int,
    val day: String,
    val type: String,
    val groupName: String,
    val userScheduleAttendances: List<GroupScheduleParticipantResponseModel>,
)

