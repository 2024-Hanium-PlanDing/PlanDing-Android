package com.comst.domain.usecase.local

import com.comst.domain.repository.LocalRepository
import javax.inject.Inject

class ClearUserDataUseCase @Inject constructor(
    private val localRepository: LocalRepository
) {
    suspend operator fun invoke(): Result<Unit> = localRepository.clearUserData().fold(
        onSuccess = {
            Result.success(Unit)
        },
        onFailure = { exception ->
            Result.failure(exception)
        }
    )
}