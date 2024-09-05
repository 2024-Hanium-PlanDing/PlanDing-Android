package com.comst.data.repository

import com.comst.data.model.base.toDomainModel
import com.comst.data.model.groupSchedule.toDomainModel
import com.comst.data.retrofit.ApiHandler
import com.comst.data.retrofit.GroupScheduleService
import com.comst.domain.model.base.SchedulePeriodModel
import com.comst.domain.model.base.CommonScheduleResponseModel
import com.comst.domain.model.groupSchedule.GroupScheduleResponseModel
import com.comst.domain.repository.GroupScheduleRepository
import com.comst.domain.util.ApiResult
import javax.inject.Inject

class GroupScheduleRepositoryImpl @Inject constructor(
    private val groupScheduleService: GroupScheduleService
) : GroupScheduleRepository {
    override suspend fun getGroupSchedule(
        groupCode: String,
        scheduleId: Long
    ): ApiResult<GroupScheduleResponseModel> {
        return ApiHandler.handle(
            execute = {
                groupScheduleService.getGroupScheduleApi(
                    groupCode = groupCode,
                    scheduleId = scheduleId,
                )
            },
            mapper = { response -> response.toDomainModel() }
        )
    }

    override suspend fun getGroupScheduleList(
        groupCode: String,
        schedulePeriodModel: SchedulePeriodModel
    ): ApiResult<List<CommonScheduleResponseModel>> {
        return ApiHandler.handle(
            execute = {
                groupScheduleService.getGroupSchedulesApi(
                    groupCode = groupCode,
                    startDate = schedulePeriodModel.startDate,
                    endDate = schedulePeriodModel.endDate
                )
            },
            mapper = { response -> response.map { it.toDomainModel() } }
        )
    }


}