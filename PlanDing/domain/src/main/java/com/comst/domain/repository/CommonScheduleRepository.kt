package com.comst.domain.repository

import com.comst.domain.model.base.ScheduleEvent
import com.comst.domain.util.ApiResult

interface CommonScheduleRepository {

    suspend fun getCommonScheduleTodayList(): ApiResult<List<ScheduleEvent>>
    suspend fun getCommonScheduleWeekList(
        startDate: String,
        endDate: String
    ): ApiResult<List<ScheduleEvent>>
}