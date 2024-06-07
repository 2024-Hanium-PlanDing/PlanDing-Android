package com.comst.data.usecase.personalSchedule

import com.comst.data.model.base.ScheduleParam
import com.comst.data.model.base.toDomainModel
import com.comst.data.retrofit.PersonalScheduleService
import com.comst.domain.model.base.ScheduleEvent
import com.comst.domain.model.base.ScheduleModel
import com.comst.domain.usecase.personalSchedule.PostPersonalScheduleUseCase
import javax.inject.Inject

class PostPersonalScheduleUseCaseImpl @Inject constructor(
    private val personalScheduleService: PersonalScheduleService
) : PostPersonalScheduleUseCase{
    override suspend fun invoke(scheduleModel: ScheduleModel): Result<ScheduleEvent> = kotlin.runCatching {

        val request = ScheduleParam(
            title = scheduleModel.title,
            content = scheduleModel.content,
            scheduleDate = scheduleModel.scheduleDate,
            startTime = scheduleModel.startTime,
            endTime = scheduleModel.endTime
        )

        personalScheduleService.postPersonalSchedule(request).data.toDomainModel()
    }
}