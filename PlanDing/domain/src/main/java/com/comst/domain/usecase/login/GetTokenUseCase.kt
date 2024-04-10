package com.comst.domain.usecase.login

interface GetTokenUseCase {
    suspend operator fun invoke() : String?
}