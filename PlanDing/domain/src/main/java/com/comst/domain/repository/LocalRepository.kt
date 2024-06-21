package com.comst.domain.repository

interface LocalRepository {
    suspend fun clearToken()
    suspend fun getToken(): String?
    suspend fun setToken(accessToken: String, refreshToken: String)
}