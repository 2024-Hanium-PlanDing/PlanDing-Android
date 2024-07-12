package com.comst.data.repository

import com.comst.data.UserDataStore
import com.comst.domain.repository.LocalRepository
import javax.inject.Inject

class LocalRepositoryImpl @Inject constructor(
    private val userDataStore: UserDataStore
): LocalRepository {

    override suspend fun clearUserData(): Result<Unit> = kotlin.runCatching{
        userDataStore.clear()
    }

    override suspend fun getToken(): Result<String?> = kotlin.runCatching{
        userDataStore.getAccessToken()
    }

    override suspend fun getUserCode(): Result<String?> = kotlin.runCatching {
        userDataStore.getUserCode()
    }

    override suspend fun setUserData(accessToken: String, refreshToken: String, userCode: String): Result<Unit> = kotlin.runCatching{
        userDataStore.setAccessToken(accessToken)
        userDataStore.setRefreshToken(refreshToken)
        userDataStore.setUserCode(userCode)
    }
}