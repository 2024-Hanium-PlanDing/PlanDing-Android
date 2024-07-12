package com.comst.domain.usecase.local

import com.comst.domain.repository.LocalRepository
import javax.inject.Inject

class SetUserDataUseCase @Inject constructor(
    private val localRepository: LocalRepository
) {
    suspend operator fun invoke(accessToken: String, refreshToken: String, userCode: String): Result<Unit> = localRepository.setUserData(accessToken, refreshToken, userCode).fold(
        onSuccess = {
            Result.success(Unit)
        },
        onFailure = { exception ->
            Result.failure(exception)
        }
    )
}