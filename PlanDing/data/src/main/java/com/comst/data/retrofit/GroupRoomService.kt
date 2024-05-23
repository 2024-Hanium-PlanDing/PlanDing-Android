package com.comst.data.retrofit

import com.comst.data.model.BaseResponse
import com.comst.data.model.groupRoom.GroupCreateParam
import com.comst.data.model.groupRoom.GroupCreateResponseDTO
import com.comst.data.model.groupRoom.MyGroupRoomDTO
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface GroupRoomService {

    @Multipart
    @POST("v1/group")
    suspend fun postGroupRoom(
        @Part("request") requestBody: GroupCreateParam,
        @Part thumbnail: MultipartBody.Part
    ): BaseResponse<GroupCreateResponseDTO>


    @GET("v1/group")
    suspend fun getMyGroupRoom():BaseResponse<List<MyGroupRoomDTO>>
}