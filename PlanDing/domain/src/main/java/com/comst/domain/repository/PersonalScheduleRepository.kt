package com.comst.domain.repository

import com.comst.domain.model.base.Schedule
import com.comst.domain.model.base.ScheduleModel
import com.comst.domain.model.base.SchedulePeriodModel
import com.comst.domain.util.ApiResult

interface PersonalScheduleRepository {

    suspend fun postPersonalSchedule(scheduleModel: ScheduleModel): ApiResult<Schedule>

    suspend fun getPersonalScheduleList(
        request: SchedulePeriodModel
    ): ApiResult<List<Schedule>>
}