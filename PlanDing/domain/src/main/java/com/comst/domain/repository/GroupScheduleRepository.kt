package com.comst.domain.repository

import com.comst.domain.model.base.Schedule
import com.comst.domain.model.base.SchedulePeriodModel
import com.comst.domain.util.ApiResult

interface GroupScheduleRepository {

    suspend fun getGroupSchedule(
        groupCode: String,
        schedulePeriodModel: SchedulePeriodModel
    ): ApiResult<List<Schedule>>
}