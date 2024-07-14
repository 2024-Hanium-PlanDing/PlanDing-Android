package com.comst.data.retrofit

import com.comst.data.model.BaseResponse
import com.comst.data.model.user.LoginResponseDTO
import com.comst.data.model.user.SocialLoginParam
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UnAuthenticatedUserService {
    @POST("v1/login/android/kakao")
    suspend fun postSocialLoginApi(
        @Body requestBody: SocialLoginParam
    ): Response<BaseResponse<LoginResponseDTO>>

}