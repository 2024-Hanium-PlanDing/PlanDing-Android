package com.comst.data.retrofit

import com.comst.data.model.BaseResponse
import com.comst.data.model.groupTask.GroupTaskResponseDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface GroupTaskService {
    @GET("v1/group-rooms/{groupCode}/planner/{scheduleId}")
    suspend fun getTasksOfScheduleApi(
        @Path("groupCode") groupCode: String,
        @Path("scheduleId") scheduleId: Long,
    ): Response<BaseResponse<GroupTaskResponseDTO>>
}