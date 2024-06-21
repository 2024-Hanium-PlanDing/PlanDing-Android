package com.comst.data.repository

import com.comst.data.UserDataStore
import com.comst.domain.model.user.LoginResponseModel
import com.comst.domain.repository.LocalRepository
import javax.inject.Inject

class LocalRepositoryImpl @Inject constructor(
    private val userDataStore: UserDataStore
): LocalRepository {

    override suspend fun clearToken(): Result<Unit> = kotlin.runCatching{
        userDataStore.clear()
    }

    override suspend fun getToken(): Result<String?> = kotlin.runCatching{
        userDataStore.getAccessToken()
    }

    override suspend fun setToken(accessToken: String, refreshToken: String): Result<Unit> = kotlin.runCatching{
        userDataStore.setAccessToken(accessToken)
        userDataStore.setRefreshToken(refreshToken)
    }
}