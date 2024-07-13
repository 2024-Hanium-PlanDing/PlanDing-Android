package com.comst.domain.usecase.groupSchedule

import com.comst.domain.model.base.Schedule
import com.comst.domain.model.base.SchedulePeriodModel
import com.comst.domain.repository.GroupScheduleRepository
import com.comst.domain.util.ApiResult
import javax.inject.Inject

class GetGroupScheduleUseCase @Inject constructor(
    private val groupScheduleRepository: GroupScheduleRepository
) {
    suspend operator fun invoke(
        groupCode: String,
        schedulePeriodModel: SchedulePeriodModel
    ): ApiResult<List<Schedule>>{
        return groupScheduleRepository.getGroupSchedule(
            groupCode = groupCode,
            schedulePeriodModel = schedulePeriodModel
        )
    }
}