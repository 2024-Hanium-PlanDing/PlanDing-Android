package com.comst.data.repository

import com.comst.data.model.user.SocialLoginParam
import com.comst.data.model.user.toDomainModel
import com.comst.data.retrofit.ApiHandler
import com.comst.data.retrofit.AuthenticatedUserService
import com.comst.data.retrofit.UnAuthenticatedUserService
import com.comst.domain.model.user.LoginResponseModel
import com.comst.domain.model.user.SocialLoginInformation
import com.comst.domain.model.user.UserProfile
import com.comst.domain.repository.UserRepository
import com.comst.domain.util.ApiResult
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val unAuthenticatedUserService: UnAuthenticatedUserService,
    private val authenticatedUserService: AuthenticatedUserService,
): UserRepository {

    override suspend fun getUserProfile(): ApiResult<UserProfile> {
        return ApiHandler.handle(
            execute = { authenticatedUserService.getUserProfileApi() },
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
            execute = { unAuthenticatedUserService.postSocialLoginApi(requestBody) },
            mapper = { response -> response.toDomainModel() }
        )
    }
}