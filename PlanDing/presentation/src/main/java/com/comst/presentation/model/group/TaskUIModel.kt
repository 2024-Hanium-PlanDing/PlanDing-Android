package com.comst.presentation.model.group

data class TaskUIModel(
    val todoId: Long,
    val scheduleId: Long,
    val assigneeName: String,
    val isCompleted: Boolean,
    val task: String
)
