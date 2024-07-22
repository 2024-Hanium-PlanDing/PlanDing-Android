package com.comst.data.retrofit

import com.comst.data.model.BaseResponse
import com.comst.data.model.user.FCMTokenParam
import com.comst.data.model.user.LoginResponseDTO
import com.comst.data.model.user.SocialLoginParam
import com.comst.data.model.user.UserProfileResponseDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthenticatedUserService {

    @POST("v1/fcm/token")
    suspend fun postFCMTokenApi(
        @Body fcmToken: FCMTokenParam
    ): Response<BaseResponse<Unit>>

    @GET("v1/profile")
    suspend fun getUserProfileApi():Response<BaseResponse<UserProfileResponseDTO>>
}