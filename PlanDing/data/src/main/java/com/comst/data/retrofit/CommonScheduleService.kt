package com.comst.data.retrofit

import com.comst.data.model.BaseResponse
import com.comst.data.model.commonSchedule.ScheduleResponseDTO
import retrofit2.http.GET

interface CommonScheduleService {

    @GET("v1/common/schedule/today")
    suspend fun getCommonScheduleToday(): BaseResponse<List<ScheduleResponseDTO>>
}