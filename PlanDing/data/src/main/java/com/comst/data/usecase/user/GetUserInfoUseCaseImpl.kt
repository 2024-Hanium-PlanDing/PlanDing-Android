package com.comst.data.usecase.user

import com.comst.data.model.user.toDomainModel
import com.comst.data.retrofit.UserService
import com.comst.domain.model.UserInfo
import com.comst.domain.usecase.user.GetUserInfoUseCase
import javax.inject.Inject

class GetUserInfoUseCaseImpl @Inject constructor(
    private val userService: UserService
): GetUserInfoUseCase{
    override suspend fun invoke(): Result<UserInfo> = kotlin.runCatching {
        userService.getUserInfo().data.toDomainModel()
    }
}