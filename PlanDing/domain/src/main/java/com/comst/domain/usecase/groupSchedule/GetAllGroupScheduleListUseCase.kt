package com.comst.domain.usecase.groupSchedule

import com.comst.domain.model.base.Schedule
import com.comst.domain.model.base.SchedulePeriodModel
import com.comst.domain.repository.GroupScheduleRepository
import com.comst.domain.util.ApiResult
import javax.inject.Inject

class GetAllGroupScheduleListUseCase @Inject constructor(
    private val groupScheduleRepository: GroupScheduleRepository
) {
    suspend operator fun invoke(
        schedulePeriodModel: SchedulePeriodModel
    ): ApiResult<List<Schedule>> {
        return groupScheduleRepository.getAllGroupScheduleList(
            schedulePeriodModel = schedulePeriodModel
        )
    }
}