package com.comst.domain.usecase.user

import com.comst.domain.model.UserInfo

interface GetUserInfoUseCase {

    suspend operator fun invoke():Result<UserInfo>
}