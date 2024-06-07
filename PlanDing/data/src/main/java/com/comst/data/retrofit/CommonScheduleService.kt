package com.comst.data.retrofit

import com.comst.data.model.BaseResponse
import com.comst.data.model.base.ScheduleResponseDTO
import retrofit2.http.GET
import retrofit2.http.Path

interface CommonScheduleService {

    @GET("v1/common/schedule/today")
    suspend fun getCommonScheduleToday(): BaseResponse<List<ScheduleResponseDTO>>

    @GET("v1/common/schedule/week/{startDate}/{endDate}")
    suspend fun getCommonScheduleWeek(
        @Path("startDate") startDate: String,
        @Path("endDate") endDate: String
    ): BaseResponse<List<ScheduleResponseDTO>>
}