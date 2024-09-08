package com.comst.data.retrofit

import com.comst.data.model.BaseResponse
import com.comst.data.model.base.CommonScheduleResponseDTO
import com.comst.data.model.groupSchedule.GroupScheduleResponseDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface GroupScheduleService {
    @GET("v1/group-rooms/{groupCode}/{scheduleId}")
    suspend fun getGroupScheduleApi(
        @Path("groupCode") groupCode: String,
        @Path("scheduleId") scheduleId: Long
    ): Response<BaseResponse<GroupScheduleResponseDTO>>

    @GET("v1/group-rooms/week/{groupCode}/{startDate}/{endDate}")
    suspend fun getGroupSchedulesApi(
        @Path("groupCode") groupCode: String,
        @Path("startDate") startDate: String,
        @Path("endDate") endDate: String
    ): Response<BaseResponse<List<CommonScheduleResponseDTO>>>

}