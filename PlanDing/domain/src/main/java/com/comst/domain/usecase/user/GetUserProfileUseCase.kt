package com.comst.domain.usecase.user

import com.comst.domain.model.user.UserProfile
import com.comst.domain.repository.UserRepository
import com.comst.domain.util.ApiResult
import javax.inject.Inject

class GetUserProfileUseCase @Inject constructor(
    private val userRepository: UserRepository
){
    suspend operator fun invoke(): ApiResult<UserProfile>{
        return userRepository.getUserProfile()
    }
}