package com.comst.data.retrofit

import com.comst.data.di.RetrofitModule.NETWORK_EXCEPTION_BODY_IS_NULL
import com.comst.data.model.BaseResponse
import com.comst.domain.util.ApiResult
import retrofit2.Response

object ApiHandler  {
    suspend fun <T : Any, R : Any> handle(
        execute: suspend () -> Response<BaseResponse<T>>,
        mapper: (T) -> R
    ): ApiResult<R> {

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
                    throw NullPointerException(NETWORK_EXCEPTION_BODY_IS_NULL)
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