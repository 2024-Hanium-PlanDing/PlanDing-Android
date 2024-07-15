package com.comst.presentation.model.group

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ReceiveScheduleDTO(
    val content: String,
    val endTime: Int,
    val groupName: String,
    val id: Int,
    val scheduleDate: String,
    val startTime: Int,
    val title: String,
    val type: String // 추가 필드
)

@JsonClass(generateAdapter = true)
data class BaseResponse<T>(
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