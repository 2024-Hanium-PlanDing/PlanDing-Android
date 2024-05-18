package com.comst.domain.usecase.user

import com.comst.domain.model.UserProfile

interface GetUserProfileUseCase {
    suspend operator fun invoke():Result<UserProfile>
}