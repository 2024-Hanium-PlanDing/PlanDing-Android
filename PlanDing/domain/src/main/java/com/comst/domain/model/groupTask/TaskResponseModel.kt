package com.comst.domain.model.groupTask

data class TaskResponseModel(
    val id: Long,
    val plannerNumber: Int,
    val title: String,
    val content: String,
    val status: String,
    val deadline: String,
    val manager: TaskUserResponseModel,
    val users: List<TaskUserResponseModel>,
)
