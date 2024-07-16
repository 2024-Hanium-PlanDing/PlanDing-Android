package com.comst.presentation.model.group.socket

import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class WebSocketResponse<T>(
    val success: Boolean,
    val data: T?,
    val errorResponse: ErrorResponse?
)

@JsonClass(generateAdapter = true)
data class ErrorResponse(
    val message: String,
    val errorCode: Int,
    val errors: List<FieldError>,
    val status: String
)

@JsonClass(generateAdapter = true)
data class FieldError(
    val field: String,
    val value: String,
    val reason: String
)