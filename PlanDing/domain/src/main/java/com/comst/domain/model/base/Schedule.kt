package com.comst.domain.model.base

data class Schedule(
    val scheduleId: Long,
    val title: String,
    val content: String,
    val startTime: Int,
    val endTime: Int,
    val day: String,
    val complete: Boolean,
    val groupName: String? = null,
)