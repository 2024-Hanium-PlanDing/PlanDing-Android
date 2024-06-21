package com.comst.domain.repository

interface LocalRepository {
    suspend fun clearToken(): Result<Unit>
    suspend fun getToken(): Result<String?>
    suspend fun setToken(accessToken: String, refreshToken: String): Result<Unit>
}