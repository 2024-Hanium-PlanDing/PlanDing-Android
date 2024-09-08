package com.comst.domain.usecase.groupSchedule

import com.comst.domain.model.groupSchedule.GroupScheduleResponseModel
import com.comst.domain.repository.GroupScheduleRepository
import com.comst.domain.util.ApiResult
import javax.inject.Inject

class GetGroupScheduleUseCase @Inject constructor(
    private val groupScheduleRepository: GroupScheduleRepository
) {
    suspend operator fun invoke(
        groupCode: String,
        scheduleId: Long
    ): ApiResult<GroupScheduleResponseModel> {
        return groupScheduleRepository.getGroupSchedule(
            groupCode = groupCode,
            scheduleId = scheduleId
        )
    }
}