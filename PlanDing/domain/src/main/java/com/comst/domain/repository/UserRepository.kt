package com.comst.domain.repository

import com.comst.domain.model.user.LoginResponseModel
import com.comst.domain.model.user.SocialLoginInfo
import com.comst.domain.model.user.UserProfile
import com.comst.domain.util.ApiResult

interface UserRepository {
    suspend fun getUserProfile(): ApiResult<UserProfile>
    suspend fun postSocialLogin(socialLoginInfo: SocialLoginInfo): ApiResult<LoginResponseModel>
}