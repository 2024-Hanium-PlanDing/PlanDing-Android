package com.comst.domain.usecase.commonSchedule

import com.comst.domain.model.base.ScheduleEvent

interface GetCommonScheduleWeekListUseCase {
    suspend operator fun invoke(
        startDate: String,
        endDate: String
    ):Result<List<ScheduleEvent>>
}