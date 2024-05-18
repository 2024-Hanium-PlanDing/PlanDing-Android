package com.comst.data.usecase.login

import com.comst.data.model.user.SocialLoginParam
import com.comst.data.model.user.toDomainModel
import com.comst.data.retrofit.UserService
import com.comst.domain.model.LoginResponseModel
import com.comst.domain.model.SocialLoginInfo
import com.comst.domain.usecase.login.SocialLoginUseCase
import javax.inject.Inject

class SocialLoginUseCaseImpl @Inject constructor(
    private val userService: UserService,
): SocialLoginUseCase{

    override suspend fun invoke(socialLoginInfo: SocialLoginInfo):Result<LoginResponseModel> = kotlin.runCatching {

        val requestBody = SocialLoginParam(
            profileNickname = socialLoginInfo.profileNickname,
            accountEmail = socialLoginInfo.accountEmail,
            profileImage = socialLoginInfo.profileImage,
            socialId = socialLoginInfo.socialId
        )


        userService.socialLogin(requestBody = requestBody).data.toDomainModel()
    }
}