package com.comst.domain.repository

import com.comst.domain.model.user.LoginResponseModel
import com.comst.domain.model.user.SocialLoginInformation
import com.comst.domain.model.user.UserProfile
import com.comst.domain.util.ApiResult

interface UserRepository {
    suspend fun getUserProfile(): ApiResult<UserProfile>
    suspend fun postSocialLogin(socialLoginInformation: SocialLoginInformation): ApiResult<LoginResponseModel>
}