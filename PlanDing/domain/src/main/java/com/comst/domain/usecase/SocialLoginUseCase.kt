package com.comst.domain.usecase

import com.comst.domain.model.user.LoginResponseModel
import com.comst.domain.model.user.SocialLoginInfo

interface SocialLoginUseCase {
    suspend operator fun invoke(socialLoginInfo: SocialLoginInfo):Result<LoginResponseModel>
}