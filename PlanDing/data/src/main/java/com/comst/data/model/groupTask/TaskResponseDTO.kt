package com.comst.data.model.groupTask

import com.comst.domain.model.groupTask.TaskResponseModel

data class TaskResponseDTO(
    val id: Long,
    val plannerNumber: Int,
    val title: String,
    val content: String,
    val status: String,
    val deadline: String,
    val manager: TaskUserResponseDTO,
    val users: List<TaskUserResponseDTO>,
)

fun TaskResponseDTO.toDomainModel(): TaskResponseModel {
    return TaskResponseModel(
        id = id,
        plannerNumber = plannerNumber,
        title = title,
        content = content,
        status = status,
        deadline = deadline,
        manager = manager.toDomainModel(),
        users = users.map { it.toDomainModel() }
    )
}