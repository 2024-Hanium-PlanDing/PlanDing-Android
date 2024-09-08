package com.comst.data.retrofit

import android.content.Context
import com.comst.data.BuildConfig.BASE_URL
import com.comst.data.UserDataStore
import com.comst.domain.util.ReAuthenticationRequiredException
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class TokenAuthenticator @Inject constructor(
    private val userDataStore: UserDataStore,
    private val context: Context
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        val isPathRefresh = response.request.url.toString() == BASE_URL + "오호"

        val refreshToken = runBlocking { userDataStore.getRefreshToken() }

        if (refreshToken.isNullOrEmpty()) {
            handleTokenExpiration(response)
            return null
        }

        if (response.code == 401 && !isPathRefresh) {
            return if (fetchUpdateToken()) {
                val newToken = runBlocking { userDataStore.getAccessToken() }
                response.request.newBuilder().apply {
                    removeHeader("Authorization")
                    addHeader("Authorization", "Bearer $newToken")
                }.build()
            } else {
                handleTokenExpiration(response)
                return null
            }
        }
        return null
    }

    private fun fetchUpdateToken(): Boolean = runBlocking {
        // 토큰 갱신 로직을 추가합니다.
        // 갱신에 실패할 경우 false를 반환합니다.
        return@runBlocking false
    }

    private fun handleTokenExpiration(response: Response) {
        runBlocking {
            userDataStore.clear()
        }
        throw ReAuthenticationRequiredException(Throwable("User needs to re-authenticate"), response.request.url.toString())
    }
}