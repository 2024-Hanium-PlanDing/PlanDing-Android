package com.comst.domain.repository

import com.comst.domain.model.groupTask.GroupTaskResponseModel
import com.comst.domain.util.ApiResult

interface GroupTaskRepository {

    suspend fun getTasksOfSchedule(
        groupCode: String,
        scheduleId: Long,
    ): ApiResult<GroupTaskResponseModel>
}