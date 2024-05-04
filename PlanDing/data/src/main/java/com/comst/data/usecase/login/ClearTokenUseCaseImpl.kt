package com.comst.data.usecase.login

import com.comst.data.UserDataStore
import com.comst.domain.usecase.login.ClearTokenUseCase
import javax.inject.Inject


class ClearTokenUseCaseImpl @Inject constructor(
    private val userDataStore: UserDataStore
) : ClearTokenUseCase {
    override suspend fun invoke(): Result<Unit> = kotlin.runCatching {
        userDataStore.clear()
    }
}