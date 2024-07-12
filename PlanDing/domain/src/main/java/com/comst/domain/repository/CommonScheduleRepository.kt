package com.comst.domain.repository

import com.comst.domain.model.base.Schedule
import com.comst.domain.model.base.SchedulePeriodModel
import com.comst.domain.util.ApiResult

interface CommonScheduleRepository {

    suspend fun getCommonScheduleTodayList(): ApiResult<List<Schedule>>
    suspend fun getCommonScheduleWeekList(
        request: SchedulePeriodModel
    ): ApiResult<List<Schedule>>
}