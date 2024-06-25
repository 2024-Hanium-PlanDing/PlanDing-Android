package com.comst.domain.usecase.commonSchedule

import com.comst.domain.model.base.ScheduleEvent
import com.comst.domain.repository.CommonScheduleRepository
import com.comst.domain.util.ApiResult
import javax.inject.Inject

class GetCommonScheduleTodayListUseCase @Inject constructor(
    private val commonScheduleRepository: CommonScheduleRepository
){
    suspend operator fun invoke(): ApiResult<List<ScheduleEvent>> {
        return commonScheduleRepository.getCommonScheduleTodayList()
    }
}