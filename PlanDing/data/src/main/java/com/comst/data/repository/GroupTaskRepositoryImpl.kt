package com.comst.data.repository

import com.comst.data.model.groupTask.toDomainModel
import com.comst.data.retrofit.ApiHandler
import com.comst.data.retrofit.GroupTaskService
import com.comst.domain.model.groupTask.GroupTaskResponseModel
import com.comst.domain.repository.GroupTaskRepository
import com.comst.domain.util.ApiResult
import javax.inject.Inject

class GroupTaskRepositoryImpl @Inject constructor(
    private val groupTaskService: GroupTaskService
): GroupTaskRepository {

    override suspend fun getTasksOfSchedule(
        groupCode: String,
        scheduleId: Long
    ): ApiResult<GroupTaskResponseModel> {
        return ApiHandler.handle(
            execute = { groupTaskService.getTasksOfScheduleApi(
                groupCode = groupCode,
                scheduleId = scheduleId
            ) },
            mapper = { response -> response.toDomainModel() }
        )
    }
}