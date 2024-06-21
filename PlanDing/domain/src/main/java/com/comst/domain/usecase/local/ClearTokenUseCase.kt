package com.comst.domain.usecase.local

import com.comst.domain.repository.LocalRepository
import javax.inject.Inject

class ClearTokenUseCase @Inject constructor(
    private val localRepository: LocalRepository
) {
    suspend operator fun invoke(): Result<Unit> = localRepository.clearToken().fold(
        onSuccess = {
            Result.success(Unit)
        },
        onFailure = { exception ->
            Result.failure(exception)
        }
    )
}