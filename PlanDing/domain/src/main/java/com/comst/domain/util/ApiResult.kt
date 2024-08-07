package com.comst.domain.util

sealed class ApiResult<out T> {
    data class Success<out T>(val data: T) : ApiResult<T>()
    data class Failure(val statusCode: Int) : ApiResult<Nothing>()
    data class Error(val exception: Exception) : ApiResult<Nothing>()
    object Loading : ApiResult<Nothing>()
}

inline fun <T> ApiResult<T>.onSuccess(action: (T) -> Unit): ApiResult<T> {
    if (this is ApiResult.Success) {
        action(data)
    }
    return this
}

inline fun <T> ApiResult<T>.onFailure(action: (Int) -> Unit): ApiResult<T> {
    if (this is ApiResult.Failure) {
        action(this.statusCode)
    }
    return this
}

inline fun <T> ApiResult<T>.onError(action: (Exception) -> Unit): ApiResult<T> {
    if (this is ApiResult.Failure) {
        action(IllegalArgumentException("code : ${this.statusCode}"))
    } else if (this is ApiResult.Error) {
        action(this.exception)
    }
    return this
}

inline fun <T> ApiResult<T>.onException(action: (Exception) -> Unit): ApiResult<T> {
    if (this is ApiResult.Error) {
        action(this.exception)
    }
    return this
}

inline fun <T, R> ApiResult<T>.map(transform: (T) -> R): ApiResult<R> =
    when (this) {
        is ApiResult.Success -> ApiResult.Success(transform(data))
        is ApiResult.Failure -> ApiResult.Failure(this.statusCode)
        is ApiResult.Error -> ApiResult.Error(this.exception)
        ApiResult.Loading -> ApiResult.Loading
    }