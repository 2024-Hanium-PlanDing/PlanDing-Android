package com.comst.data.repository

import com.comst.data.model.base.toDomainModel
import com.comst.data.retrofit.ApiHandler
import com.comst.data.retrofit.CommonScheduleService
import com.comst.domain.model.base.ScheduleEvent
import com.comst.domain.model.base.SchedulePeriodModel
import com.comst.domain.repository.CommonScheduleRepository
import com.comst.domain.util.ApiResult
import javax.inject.Inject

class CommonScheduleRepositoryImpl @Inject constructor(
    private val commonScheduleService: CommonScheduleService,
): CommonScheduleRepository {

    override suspend fun getCommonScheduleTodayList(): ApiResult<List<ScheduleEvent>> {
        return ApiHandler.handle(
            execute = { commonScheduleService.getCommonScheduleToday() },
            mapper = { response -> response.map { it.toDomainModel() } }
        )
    }

    override suspend fun getCommonScheduleWeekList(
        request: SchedulePeriodModel
    ): ApiResult<List<ScheduleEvent>> {
        return ApiHandler.handle(
            execute = { commonScheduleService.getCommonScheduleWeek(
                startDate = request.startDate,
                endDate = request.endDate
            ) },
            mapper = { response -> response.map { it.toDomainModel() } }
        )
    }
}