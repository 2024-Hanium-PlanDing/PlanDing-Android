package com.comst.domain.usecase.login

import com.comst.domain.model.LoginResponseModel
import com.comst.domain.model.SocialLoginInfo

interface SocialLoginUseCase {
    suspend operator fun invoke(socialLoginInfo: SocialLoginInfo):Result<LoginResponseModel>
}