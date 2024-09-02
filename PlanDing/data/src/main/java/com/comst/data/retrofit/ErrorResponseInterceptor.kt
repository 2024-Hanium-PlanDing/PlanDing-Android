package com.comst.data.retrofit

import com.comst.data.model.ErrorResponse
import com.comst.domain.util.AccountNotFoundException
import com.comst.domain.util.BadRequestException
import com.comst.domain.util.InternalServerErrorException
import com.comst.domain.util.ServerNotFoundException
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject

class ErrorResponseInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        try {
            val response = chain.proceed(request)
            val responseBody = response.body

            if (response.isSuccessful) return response

            val requestUrl = request.url.toString()
            val errorResponse = responseBody?.string()?.let { createErrorResponse(it) }
            val errorException = createErrorException(requestUrl, response.code, errorResponse)
            errorException?.let { throw it }

            return response
        } catch (e: Throwable) {
            when (e) {
                is IOException -> throw e
                else -> throw IOException(e)
            }
        }
    }

    private fun createErrorResponse(responseBodyString: String): ErrorResponse? =
        try {
            Gson().fromJson(responseBodyString, ErrorResponse::class.java)
        } catch (e: JsonSyntaxException) {
            null
        }

    private fun createErrorException(url: String?, httpCode: Int, errorResponse: ErrorResponse?): IOException? =
        when (httpCode) {
            400, 403 -> BadRequestException(Throwable(errorResponse?.message), url)
            402 -> AccountNotFoundException(Throwable(errorResponse?.message), url)
            404 -> ServerNotFoundException(Throwable(errorResponse?.message), url)
            500 -> InternalServerErrorException(Throwable(errorResponse?.message), url)
            502 -> BadRequestException(Throwable(errorResponse?.message), url)
            else -> null
        }
}