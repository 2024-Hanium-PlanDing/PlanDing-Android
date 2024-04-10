package com.comst.domain.usecase.login

interface SetTokenUseCase {
    suspend operator fun invoke(
        accessToken : String,
        refreshToken : String
    )
}