package com.comst.data.retrofit

import com.comst.data.model.BaseResponse
import com.comst.data.model.chat.ChatMessageParam
import com.comst.data.model.chat.ChatMessageResponseDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ChatService {

    @GET("v1/chat/{groupCode}/message")
    suspend fun getGroupChatsApi(
        @Path("groupCode") groupCode: String
    ): Response<BaseResponse<List<ChatMessageResponseDTO>>>

    @POST("v1/chat/{groupCode}")
    suspend fun postChatApi(
        @Path("groupCode") groupCode: String,
        @Body requestBody: ChatMessageParam
    ): Response<BaseResponse<ChatMessageResponseDTO>>
}