package com.comst.data.repository

import com.comst.data.model.base.ScheduleParam
import com.comst.data.model.base.toDomainModel
import com.comst.data.retrofit.ApiHandler
import com.comst.data.retrofit.PersonalScheduleService
import com.comst.domain.model.base.ScheduleEvent
import com.comst.domain.model.base.ScheduleModel
import com.comst.domain.repository.PersonalScheduleRepository
import com.comst.domain.util.ApiResult
import javax.inject.Inject

class PersonalScheduleRepositoryImpl @Inject constructor(
    private val personalScheduleService: PersonalScheduleService,
    private val apiHandler: ApiHandler
): PersonalScheduleRepository {

    override suspend fun postPersonalSchedule(scheduleModel: ScheduleModel): ApiResult<ScheduleEvent> {
        val request = ScheduleParam(
            title = scheduleModel.title,
            content = scheduleModel.content,
            scheduleDate = scheduleModel.scheduleDate,
            startTime = scheduleModel.startTime,
            endTime = scheduleModel.endTime
        )
        return apiHandler.handle(
            execute = { personalScheduleService.postPersonalSchedule(request) },
            mapper = { response -> response.toDomainModel() }
        )
    }

}