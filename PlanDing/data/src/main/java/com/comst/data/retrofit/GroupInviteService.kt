package com.comst.data.retrofit

import com.comst.data.model.BaseResponse
import com.comst.data.model.groupInvite.GroupInviteAcceptResponseDTO
import com.comst.data.model.groupInvite.GroupInviteParam
import com.comst.data.model.groupInvite.GroupInviteResponseDTO
import com.comst.data.model.groupInvite.GroupRequestReceivedResponseDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface GroupInviteService {

    @GET("v1/invitation")
    suspend fun getGroupRequestsReceivedApi(): Response<BaseResponse<List<GroupRequestReceivedResponseDTO>>>

    @POST("v1/invitation")
    suspend fun postGroupInviteApi(
        @Body requestBody: GroupInviteParam
    ): Response<BaseResponse<GroupInviteResponseDTO>>

    @GET("v1/invitation/accept/{groupCode}/{inviteCode}")
    suspend fun getAcceptGroupInviteApi(
        @Path("groupCode") groupCode: String,
        @Path("inviteCode") inviteCode: String
    ): Response<BaseResponse<GroupInviteAcceptResponseDTO>>

    @DELETE("v1/invitation/decline/{inviteCode}")
    suspend fun deleteDenyGroupInviteApi(
        @Path("inviteCode") inviteCode: String
    ): Response<BaseResponse<String>>
}