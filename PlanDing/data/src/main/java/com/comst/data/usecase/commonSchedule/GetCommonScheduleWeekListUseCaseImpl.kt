package com.comst.data.usecase.commonSchedule

import com.comst.data.model.base.toDomainModel
import com.comst.data.retrofit.CommonScheduleService
import com.comst.domain.model.base.ScheduleEvent
import com.comst.domain.usecase.commonSchedule.GetCommonScheduleWeekListUseCase
import javax.inject.Inject

class GetCommonScheduleWeekListUseCaseImpl @Inject constructor(
    private val commonScheduleService: CommonScheduleService
) : GetCommonScheduleWeekListUseCase {
    override suspend fun invoke(
        startDate: String,
        endDate: String
    ): Result<List<ScheduleEvent>> = kotlin.runCatching {
        commonScheduleService.getCommonScheduleWeek(
            startDate = startDate,
            endDate = endDate
        ).data.map { it.toDomainModel() }
    }
}