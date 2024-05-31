package com.comst.domain.usecase.commonSchedule

import com.comst.domain.model.base.ScheduleEvent

interface GetCommonScheduleTodayListUseCase {
    suspend operator fun invoke():Result<List<ScheduleEvent>>
}