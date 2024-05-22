package com.comst.data.usecase.user

import com.comst.data.model.user.toDomainModel
import com.comst.data.retrofit.UserService
import com.comst.domain.model.user.UserProfile
import com.comst.domain.usecase.user.GetUserProfileUseCase
import javax.inject.Inject

class GetUserProfileUseCaseImpl @Inject constructor(
    private val userService: UserService
) : GetUserProfileUseCase{
    override suspend fun invoke(): Result<UserProfile> = kotlin.runCatching{
        userService.getUserProfile().data.toDomainModel()
    }
}