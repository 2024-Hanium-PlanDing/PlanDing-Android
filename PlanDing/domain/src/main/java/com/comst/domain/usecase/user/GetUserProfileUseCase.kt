package com.comst.domain.usecase.user

import com.comst.domain.model.user.UserProfile

interface GetUserProfileUseCase {
    suspend operator fun invoke():Result<UserProfile>
}