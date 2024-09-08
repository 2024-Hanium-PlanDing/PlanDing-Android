package com.comst.domain.usecase.groupTask

import com.comst.domain.model.groupTask.GroupTaskResponseModel
import com.comst.domain.repository.GroupTaskRepository
import com.comst.domain.util.ApiResult
import javax.inject.Inject

class GetTaskListOfScheduleUseCase @Inject constructor(
    private val groupTaskRepository: GroupTaskRepository
){
    suspend operator fun invoke(
        groupCode: String,
        scheduleId: Long
    ): ApiResult<GroupTaskResponseModel>{
        return groupTaskRepository.getTasksOfSchedule(
            groupCode = groupCode,
            scheduleId = scheduleId
        )
    }
}