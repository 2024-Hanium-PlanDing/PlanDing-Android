package com.comst.domain.repository

import com.comst.domain.model.base.SchedulePeriodModel
import com.comst.domain.model.base.CommonScheduleResponseModel
import com.comst.domain.model.base.Schedule
import com.comst.domain.model.groupSchedule.GroupScheduleResponseModel
import com.comst.domain.util.ApiResult

interface GroupScheduleRepository {

    suspend fun getGroupSchedule(
        groupCode: String,
        scheduleId: Long
    ): ApiResult<GroupScheduleResponseModel>
    suspend fun getGroupScheduleList(
        groupCode: String,
        schedulePeriodModel: SchedulePeriodModel
    ): ApiResult<List<CommonScheduleResponseModel>>

    suspend fun getAllGroupScheduleList(
        schedulePeriodModel: SchedulePeriodModel
    ): ApiResult<List<Schedule>>
}