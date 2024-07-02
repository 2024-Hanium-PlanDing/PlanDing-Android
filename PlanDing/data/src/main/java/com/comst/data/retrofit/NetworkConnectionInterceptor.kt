package com.comst.data.retrofit

import android.content.Context
import android.util.Log
import com.comst.data.di.RetrofitModule.NETWORK_EXCEPTION_OFFLINE_CASE
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject

class NetworkConnectionInterceptor @Inject constructor(
    private val context: Context
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!NetworkUtils.isOnline(context)) {
            throw IOException(NETWORK_EXCEPTION_OFFLINE_CASE)
        }
        return chain.proceed(chain.request())
    }
}