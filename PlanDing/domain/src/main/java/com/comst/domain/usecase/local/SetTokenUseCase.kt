package com.comst.domain.usecase.local

import com.comst.domain.repository.LocalRepository
import javax.inject.Inject

class SetTokenUseCase @Inject constructor(
    private val localRepository: LocalRepository
) {
    suspend operator fun invoke(accessToken: String, refreshToken: String): Result<Unit> = kotlin.runCatching {
        localRepository.setToken(
            accessToken = accessToken,
            refreshToken = refreshToken
        )
    }
}