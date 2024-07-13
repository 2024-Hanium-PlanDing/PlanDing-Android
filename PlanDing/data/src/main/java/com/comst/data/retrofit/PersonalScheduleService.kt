package com.comst.data.retrofit

import com.comst.data.model.BaseResponse
import com.comst.data.model.base.ScheduleParam
import com.comst.data.model.base.ScheduleResponseDTO
import com.comst.data.model.personalSchedule.PersonalScheduleResponseDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface PersonalScheduleService {

    @POST("v1/schedule")
    suspend fun postPersonalScheduleApi(
        @Body requestBody: ScheduleParam
    ): Response<BaseResponse<ScheduleResponseDTO>>

    @GET("v1/schedule/week/{startDate}/{endDate}")
    suspend fun getPersonalScheduleApi(
        @Path("startDate") startDate: String,
        @Path("endDate") endDate: String
    ): Response<BaseResponse<List<PersonalScheduleResponseDTO>>>
}