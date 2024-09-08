package com.comst.data.model.groupSchedule

import com.comst.domain.model.groupSchedule.GroupScheduleParticipantResponseModel

data class GroupScheduleParticipantResponseDTO (
    val userCode: String,
    val userName: String,
    val status: String
)

fun GroupScheduleParticipantResponseDTO.toDomainModel(): GroupScheduleParticipantResponseModel{
    return GroupScheduleParticipantResponseModel(
        userCode = userCode,
        userName = userName,
        status = status
    )
}