package com.comst.domain.usecase.user

import com.comst.domain.repository.UserRepository
import com.comst.domain.util.ApiResult
import javax.inject.Inject

class PostFCMTokenUseCase @Inject constructor(
    private val userRepository: UserRepository
){
    suspend operator fun invoke(fcmToken: String): ApiResult<Unit> {
        return userRepository.postFCMToken(fcmToken)
    }
}