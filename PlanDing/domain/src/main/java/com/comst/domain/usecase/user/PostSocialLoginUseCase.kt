package com.comst.domain.usecase.user

import com.comst.domain.model.user.LoginResponseModel
import com.comst.domain.model.user.SocialLoginInfo
import com.comst.domain.repository.UserRepository
import com.comst.domain.util.ApiResult
import javax.inject.Inject

class PostSocialLoginUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(socialLoginInfo: SocialLoginInfo):ApiResult<LoginResponseModel>{
        return userRepository.postSocialLogin(socialLoginInfo)
    }
}