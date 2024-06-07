package com.comst.data.retrofit

import com.comst.data.model.BaseResponse
import com.comst.data.model.base.ScheduleParam
import com.comst.data.model.base.ScheduleResponseDTO
import retrofit2.http.Body
import retrofit2.http.POST

interface PersonalScheduleService {

    @POST("v1/schedule")
    suspend fun postPersonalSchedule(
        @Body requestBody: ScheduleParam
    ): BaseResponse<ScheduleResponseDTO>
}