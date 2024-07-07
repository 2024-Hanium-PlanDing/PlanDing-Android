package com.comst.data.retrofit

import com.comst.data.model.BaseResponse
import com.comst.data.model.group.GroupCreateParam
import com.comst.data.model.group.GroupCreateResponseDTO
import com.comst.data.model.group.MyGroupRoomDTO
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface GroupService {

    @Multipart
    @POST("v1/group")
    suspend fun postGroup(
        @Part("request") requestBody: GroupCreateParam,
        @Part thumbnail: MultipartBody.Part
    ): Response<BaseResponse<GroupCreateResponseDTO>>

    @GET("v1/group")
    suspend fun getMyGroup(): Response<BaseResponse<List<MyGroupRoomDTO>>>
}