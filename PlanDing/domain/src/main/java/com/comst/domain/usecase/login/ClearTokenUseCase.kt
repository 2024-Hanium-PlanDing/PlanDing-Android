package com.comst.domain.usecase.login

interface ClearTokenUseCase {
    suspend operator fun invoke(): Result<Unit>
}