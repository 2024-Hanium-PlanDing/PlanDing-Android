package com.comst.data.usecase.commonSchedule

import com.comst.data.model.base.toDomainModel
import com.comst.data.retrofit.CommonScheduleService
import com.comst.domain.model.base.ScheduleEvent
import com.comst.domain.usecase.commonSchedule.GetCommonScheduleTodayListUseCase
import javax.inject.Inject

class GetCommonScheduleTodayListUseCaseImpl @Inject constructor(
    private val commonScheduleService: CommonScheduleService
) : GetCommonScheduleTodayListUseCase{
    override suspend fun invoke(): Result<List<ScheduleEvent>> = kotlin.runCatching {
        commonScheduleService.getCommonScheduleToday().data.map { it.toDomainModel() }
    }.onSuccess {
        //Result.success()
    }.onFailure {

    }
}