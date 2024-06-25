package com.comst.domain.repository

import com.comst.domain.model.base.ScheduleEvent
import com.comst.domain.model.base.ScheduleModel
import com.comst.domain.util.ApiResult

interface PersonalScheduleRepository {

    suspend fun postPersonalSchedule(scheduleModel: ScheduleModel): ApiResult<ScheduleEvent>
}