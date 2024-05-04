package com.comst.data.retrofit

import com.comst.data.model.BaseResponse
import com.comst.data.model.user.LoginResponseDTO
import com.comst.data.model.user.SocialLoginParam
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.POST

interface UserService {

    @POST("v1/login/android/kakao")
    suspend fun socialLogin(
        @Body requestBody: SocialLoginParam
    ):BaseResponse<LoginResponseDTO>
}