package com.comst.data.retrofit

import android.content.Context
import com.comst.data.BuildConfig.BASE_URL
import com.comst.data.UserDataStore
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

        val refreshToken = runBlocking{ userDataStore.getRefreshToken() }

        if (refreshToken.isNullOrEmpty()) return null

        if (response.code == 401 && !isPathRefresh){
            return if ( fetchUpdateToken() ){
                val newToken = runBlocking { userDataStore.getAccessToken() }
                response.request.newBuilder().apply {
                    removeHeader("Authorization")
                    addHeader("Authorization", "Bearer $newToken")
                }.build()
            }else{
                // 로그인으로 보내기
                redirectToLogin()
                null
            }
        }
        return null
    }

    private fun fetchUpdateToken(): Boolean = runBlocking {

        return@runBlocking false
    }

    private fun redirectToLogin(){
        /*
        val intent = Intent(context, AuthActivity::class.java)
            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        context.startActivity(intent)
        */
    }

}