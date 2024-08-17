package com.comst.data.retrofit

import com.comst.data.model.BaseResponse
import com.comst.data.model.group.GroupInviteParam
import com.comst.data.model.group.GroupInviteResponseDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface GroupInviteService {

    @POST("v1/invitation")
    suspend fun postGroupInviteApi(
        @Body requestBody: GroupInviteParam
    ): Response<BaseResponse<GroupInviteResponseDTO>>
}