package com.comst.data.retrofit

import com.comst.data.model.BaseResponse
import com.comst.data.model.group.GroupInviteParam
import com.comst.data.model.group.GroupInviteResponseDTO
import com.comst.data.model.groupInvite.GroupRequestReceivedResponseDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface GroupInviteService {

    @GET("v1/invitation")
    suspend fun getGroupRequestsReceivedApi(): Response<BaseResponse<List<GroupRequestReceivedResponseDTO>>>

    @POST("v1/invitation")
    suspend fun postGroupInviteApi(
        @Body requestBody: GroupInviteParam
    ): Response<BaseResponse<GroupInviteResponseDTO>>
}