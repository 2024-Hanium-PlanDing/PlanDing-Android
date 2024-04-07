package com.comst.data.model

import kotlinx.serialization.Serializable

@Serializable
data class CommonResponse<T>(
    val success:String,
    val data: T,
    val errorResponse:T,
    val status:String
)
