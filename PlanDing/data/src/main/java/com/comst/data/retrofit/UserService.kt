package com.comst.data.retrofit

import com.comst.data.model.BaseResponse
import com.comst.data.model.user.LoginResponseDTO
import com.comst.data.model.user.SocialLoginParam
import com.comst.data.model.user.UserInfoResponseDTO
import com.comst.data.model.user.UserProfileResponseDTO
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserService {

    @POST("v1/login/android/kakao")
    suspend fun socialLogin(
        @Body requestBody: SocialLoginParam
    ):BaseResponse<LoginResponseDTO>

    @GET("v1/userInfo")
    suspend fun getUserInfo():BaseResponse<UserInfoResponseDTO>

    @GET("v1/profile")
    suspend fun getUserProfile():BaseResponse<UserProfileResponseDTO>
}