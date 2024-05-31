package com.comst.data.retrofit

import android.util.Log
import com.comst.data.UserDataStore
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class TokenInterceptor  @Inject constructor(
    private val userDataStore: UserDataStore
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token:String? = runBlocking { userDataStore.getAccessToken() }
        Log.d("앙냥",token.toString())
        return chain.proceed(
            chain.request()
                .newBuilder()
                .run {
                    if (token.isNullOrEmpty()){
                        this
                    }else{
                        this.addHeader("Authorization", "Bearer $token")
                    }
                }
                .addHeader("Content-Type","application/json;")
                .build()
        )
    }

}