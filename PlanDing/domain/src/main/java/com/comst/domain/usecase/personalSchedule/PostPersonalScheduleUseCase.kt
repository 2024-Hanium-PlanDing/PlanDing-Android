package com.comst.domain.usecase.personalSchedule

import com.comst.domain.model.base.Schedule
import com.comst.domain.model.base.ScheduleModel
import com.comst.domain.repository.PersonalScheduleRepository
import com.comst.domain.util.ApiResult
import javax.inject.Inject

class PostPersonalScheduleUseCase @Inject constructor(
    private val personalScheduleRepository: PersonalScheduleRepository
) {

    suspend operator fun invoke(scheduleModel: ScheduleModel):ApiResult<Schedule>{
        return personalScheduleRepository.postPersonalSchedule(scheduleModel)
    }
}