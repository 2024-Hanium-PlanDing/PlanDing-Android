package com.comst.domain.usecase.local

import com.comst.domain.repository.LocalRepository
import javax.inject.Inject

class SetTokenUseCase @Inject constructor(
    private val localRepository: LocalRepository
) {
    suspend operator fun invoke(accessToken: String, refreshToken: String): Result<Unit> = localRepository.setToken(accessToken, refreshToken).fold(
        onSuccess = {
            Result.success(Unit)
        },
        onFailure = { exception ->
            Result.failure(exception)
        }
    )
}