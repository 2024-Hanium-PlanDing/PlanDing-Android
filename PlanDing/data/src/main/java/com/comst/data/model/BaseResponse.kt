package com.comst.data.model

import kotlinx.serialization.Serializable

@Serializable
data class BaseResponse<T>(
    val success:Boolean,
    val data: T,
    val errorResponse:ErrorResponse,
)

@Serializable
data class ErrorResponse(
    val message: String,
    val errorCode: Int,
    val errors: List<FieldError>,
    val status: String
)

@Serializable
data class FieldError(
    val field: String,
    val value: String,
    val reason: String
)
