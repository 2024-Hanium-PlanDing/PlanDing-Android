package com.comst.domain.usecase.groupSchedule

import com.comst.domain.model.base.SchedulePeriodModel
import com.comst.domain.model.base.CommonScheduleResponseModel
import com.comst.domain.repository.GroupScheduleRepository
import com.comst.domain.util.ApiResult
import javax.inject.Inject

class GetGroupScheduleListUseCase @Inject constructor(
    private val groupScheduleRepository: GroupScheduleRepository
) {
    suspend operator fun invoke(
        groupCode: String,
        schedulePeriodModel: SchedulePeriodModel
    ): ApiResult<List<CommonScheduleResponseModel>>{
        return groupScheduleRepository.getGroupScheduleList(
            groupCode = groupCode,
            schedulePeriodModel = schedulePeriodModel
        )
    }
}