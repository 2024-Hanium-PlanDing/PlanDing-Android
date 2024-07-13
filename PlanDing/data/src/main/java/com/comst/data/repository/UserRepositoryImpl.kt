package com.comst.data.repository

import com.comst.data.model.user.SocialLoginParam
import com.comst.data.model.user.toDomainModel
import com.comst.data.retrofit.ApiHandler
import com.comst.data.retrofit.UserService
import com.comst.domain.model.user.LoginResponseModel
import com.comst.domain.model.user.SocialLoginInformation
import com.comst.domain.model.user.UserProfile
import com.comst.domain.repository.UserRepository
import com.comst.domain.util.ApiResult
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userService: UserService,
): UserRepository {

    override suspend fun getUserProfile(): ApiResult<UserProfile> {
        return ApiHandler.handle(
            execute = { userService.getUserProfileApi() },
            mapper = { response -> response.toDomainModel()}
        )
    }

    override suspend fun postSocialLogin(socialLoginInformation: SocialLoginInformation): ApiResult<LoginResponseModel> {

        val requestBody = SocialLoginParam(
            profileNickname = socialLoginInformation.profileNickname,
            accountEmail = socialLoginInformation.accountEmail,
            profileImage = socialLoginInformation.profileImage,
            socialId = socialLoginInformation.socialId
        )

        return ApiHandler.handle(
            execute = { userService.postSocialLoginApi(requestBody) },
            mapper = { response -> response.toDomainModel() }
        )
    }
}