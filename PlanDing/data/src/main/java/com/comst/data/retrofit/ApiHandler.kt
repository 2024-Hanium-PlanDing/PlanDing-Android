package com.comst.data.retrofit

import android.content.Context
import com.comst.data.model.BaseResponse
import com.comst.domain.util.ApiResult
import retrofit2.Response
import javax.inject.Inject

class ApiHandler @Inject constructor(
    private val context: Context
) {
    suspend fun <T : Any, R : Any> handle(
        execute: suspend () -> Response<BaseResponse<T>>,
        mapper: (T) -> R
    ): ApiResult<R> {
        if (!NetworkUtils.isOnline(context)) {
            return ApiResult.Error(Exception("Network is offline"))
        }

        return try {
            val response = execute()
            val body = response.body()
            if (response.isSuccessful) {
                body?.let {
                    if (it.success) {
                        ApiResult.Success(mapper(it.data))
                    } else {
                        ApiResult.Failure(it.errorResponse.errorCode, it.errorResponse.message)
                    }
                } ?: run {
                    throw NullPointerException("Result is null")
                }
            } else {
                getFailApiResult(body, response)
            }
        } catch (e: Exception) {
            ApiResult.Error(e)
        }
    }

    private fun <T : Any> getFailApiResult(body: BaseResponse<T>?, response: Response<BaseResponse<T>>) = body?.let {
        ApiResult.Failure(statusCode = response.code(), message = it.errorResponse.message)
    } ?: run {
        ApiResult.Failure(statusCode = response.code(), message = response.message())
    }
}