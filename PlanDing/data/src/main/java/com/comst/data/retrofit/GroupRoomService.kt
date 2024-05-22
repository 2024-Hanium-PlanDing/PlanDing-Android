package com.comst.data.retrofit

import com.comst.data.model.BaseResponse
import com.comst.data.model.groupRoom.GroupCreateParam
import com.comst.data.model.groupRoom.GroupCreateResponseDTO
import retrofit2.http.Body
import retrofit2.http.POST

interface GroupRoomService {

    @POST("v1/group")
    suspend fun postGroupRoom(
        @Body requestBody:GroupCreateParam
    ):BaseResponse<GroupCreateResponseDTO>
}