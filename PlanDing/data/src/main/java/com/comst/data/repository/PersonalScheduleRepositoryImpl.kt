package com.comst.data.repository

import com.comst.data.model.base.ScheduleParam
import com.comst.data.model.base.toDomainModel
import com.comst.data.model.personalSchedule.toDomainModel
import com.comst.data.retrofit.ApiHandler
import com.comst.data.retrofit.PersonalScheduleService
import com.comst.domain.model.base.Schedule
import com.comst.domain.model.base.ScheduleModel
import com.comst.domain.model.base.SchedulePeriodModel
import com.comst.domain.repository.PersonalScheduleRepository
import com.comst.domain.util.ApiResult
import javax.inject.Inject

class PersonalScheduleRepositoryImpl @Inject constructor(
    private val personalScheduleService: PersonalScheduleService,
): PersonalScheduleRepository {

    override suspend fun postPersonalSchedule(scheduleModel: ScheduleModel): ApiResult<Schedule> {
        val request = ScheduleParam(
            title = scheduleModel.title,
            content = scheduleModel.content,
            scheduleDate = scheduleModel.scheduleDate,
            startTime = scheduleModel.startTime,
            endTime = scheduleModel.endTime
        )
        return ApiHandler.handle(
            execute = { personalScheduleService.postPersonalScheduleApi(request) },
            mapper = { response -> response.toDomainModel() }
        )
    }
    override suspend fun getPersonalScheduleList(request: SchedulePeriodModel): ApiResult<List<Schedule>> {
        return ApiHandler.handle(
            execute = { personalScheduleService.getPersonalScheduleApi(
                startDate = request.startDate,
                endDate = request.endDate
            ) },
            mapper = { response -> response.map { it.toDomainModel() } }
        )
    }

}