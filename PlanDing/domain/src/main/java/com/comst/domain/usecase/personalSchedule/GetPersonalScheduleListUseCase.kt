package com.comst.domain.usecase.personalSchedule

import com.comst.domain.model.base.Schedule
import com.comst.domain.model.base.SchedulePeriodModel
import com.comst.domain.repository.PersonalScheduleRepository
import com.comst.domain.util.ApiResult
import javax.inject.Inject

class GetPersonalScheduleListUseCase @Inject constructor(
    private val personalScheduleRepository: PersonalScheduleRepository
) {

    suspend operator fun invoke(request: SchedulePeriodModel): ApiResult<List<Schedule>> {
        return personalScheduleRepository.getPersonalScheduleList(request)
    }
}