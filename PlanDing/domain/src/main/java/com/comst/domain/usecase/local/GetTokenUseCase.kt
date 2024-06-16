package com.comst.domain.usecase.local

import com.comst.domain.repository.LocalRepository
import javax.inject.Inject

class GetTokenUseCase @Inject constructor(
    private val localRepository: LocalRepository
) {
    suspend operator fun invoke(): Result<String?> = kotlin.runCatching {
        localRepository.getToken()
    }
}