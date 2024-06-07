package com.comst.domain.usecase.personalSchedule

import com.comst.domain.model.base.ScheduleEvent
import com.comst.domain.model.base.ScheduleModel

interface PostPersonalScheduleUseCase {

    suspend operator fun invoke(scheduleModel: ScheduleModel):Result<ScheduleEvent>
}