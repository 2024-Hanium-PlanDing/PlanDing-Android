package com.comst.data.repository

import com.comst.data.UserDataStore
import com.comst.domain.model.user.LoginResponseModel
import com.comst.domain.repository.LocalRepository
import javax.inject.Inject

class LocalRepositoryImpl @Inject constructor(
    private val userDataStore: UserDataStore
): LocalRepository {

    override suspend fun clearToken() {
        try {
            userDataStore.clear()
        } catch (exception: Exception) {
            // 예외 처리
        }
    }

    override suspend fun getToken(): String? {
        return try {
            userDataStore.getAccessToken()
        } catch (exception: Exception) {
            // 예외 처리
            null
        }
    }

    override suspend fun setToken(accessToken: String, refreshToken: String) {
        userDataStore.setAccessToken(accessToken)
        userDataStore.setRefreshToken(refreshToken)
    }

}