package com.comst.data.model.base

import com.comst.domain.model.base.ScheduleType
import com.comst.domain.model.base.CommonScheduleResponseModel

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