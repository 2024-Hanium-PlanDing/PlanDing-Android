package com.comst.data.retrofit

import com.comst.data.model.BaseResponse
import com.comst.data.model.base.CommonScheduleResponseDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CommonScheduleService {

    @GET("v1/common/schedule/today")
    suspend fun getCommonScheduleTodayApi(): Response<BaseResponse<List<CommonScheduleResponseDTO>>>

    @GET("v1/common/schedule/week/{startDate}/{endDate}")
    suspend fun getCommonScheduleWeekApi(
        @Path("startDate") startDate: String,
        @Path("endDate") endDate: String
    ): Response<BaseResponse<List<CommonScheduleResponseDTO>>>
}