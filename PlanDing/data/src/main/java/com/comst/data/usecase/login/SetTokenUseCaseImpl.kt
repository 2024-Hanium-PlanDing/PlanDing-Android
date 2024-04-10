package com.comst.data.usecase.login

import com.comst.data.UserDataStore
import com.comst.domain.usecase.login.SetTokenUseCase
import javax.inject.Inject

class SetTokenUseCaseImpl @Inject constructor(
    private val userDataStore: UserDataStore
) : SetTokenUseCase{

    override suspend fun invoke(accessToken: String, refreshToken: String) {
        userDataStore.setAccessToken(accessToken)
        userDataStore.setRefreshToken(refreshToken)
    }

}