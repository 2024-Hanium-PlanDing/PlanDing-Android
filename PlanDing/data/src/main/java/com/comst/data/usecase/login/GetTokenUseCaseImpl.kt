package com.comst.data.usecase.login

import android.util.Log
import com.comst.data.UserDataStore
import com.comst.domain.usecase.login.GetTokenUseCase
import javax.inject.Inject

class GetTokenUseCaseImpl @Inject constructor(
    private val userDataStore: UserDataStore
): GetTokenUseCase{
    override suspend fun invoke(): String?  {
        return userDataStore.getAccessToken()
    }

}