package com.comst.domain.usecase.user

import com.comst.domain.model.user.LoginResponseModel
import com.comst.domain.model.user.SocialLoginInformation
import com.comst.domain.repository.UserRepository
import com.comst.domain.util.ApiResult
import javax.inject.Inject

class PostSocialLoginUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(socialLoginInformation: SocialLoginInformation):ApiResult<LoginResponseModel>{
        return userRepository.postSocialLogin(socialLoginInformation)
    }
}