package com.comst.domain.repository

interface LocalRepository {
    suspend fun clearUserData(): Result<Unit>
    suspend fun getToken(): Result<String?>
    suspend fun getUserCode(): Result<String?>
    suspend fun setUserData(accessToken: String, refreshToken: String, userCode: String): Result<Unit>
}