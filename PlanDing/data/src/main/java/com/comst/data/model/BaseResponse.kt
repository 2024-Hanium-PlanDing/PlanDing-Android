package com.comst.data.model

import kotlinx.serialization.Serializable

@Serializable
data class BaseResponse<T>(
    val success:String,
    val data: T,
    val errorResponse:T,
    val status:String
)
