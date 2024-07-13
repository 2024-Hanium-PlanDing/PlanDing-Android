package com.comst.data.retrofit

import com.comst.data.model.BaseResponse
import com.comst.data.model.base.ScheduleResponseDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface GroupScheduleService {

    @GET("v1/group-rooms/week/{groupCode}/{startDate}/{endDate}")
    suspend fun getGroupSchedule(
        @Path("groupCode") groupCode: String,
        @Path("startDate") startDate: String,
        @Path("endDate") endDate: String
    ): Response<BaseResponse<List<ScheduleResponseDTO>>>
}