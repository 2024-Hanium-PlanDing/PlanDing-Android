package com.comst.data.model.base

import com.comst.domain.model.base.ScheduleType
import com.comst.domain.model.base.CommonScheduleResponseModel
import com.comst.domain.model.base.Schedule

data class CommonScheduleResponseDTO(
    val scheduleCommonResponse: ScheduleResponseDTO,
    val type: String
)

fun CommonScheduleResponseDTO.toDomainModel(): CommonScheduleResponseModel {
    return CommonScheduleResponseModel(
        scheduleCommonResponse = scheduleCommonResponse.toDomainModel(),
        type = ScheduleType.GROUP
    )
}

fun CommonScheduleResponseDTO.toDomainScheduleModel(): Schedule {
    return Schedule(
        scheduleId = scheduleCommonResponse.id,
        title = scheduleCommonResponse.title,
        content = scheduleCommonResponse.content,
        startTime = scheduleCommonResponse.startTime,
        endTime = scheduleCommonResponse.endTime,
        day = scheduleCommonResponse.scheduleDate,
        complete = false,
        groupName = scheduleCommonResponse.groupName
    )
}