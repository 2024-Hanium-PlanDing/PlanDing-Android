package com.comst.data.model.groupTask

import com.comst.domain.model.groupTask.TaskUserResponseModel

data class TaskUserResponseDTO(
    val userCode: String,
    val username: String,
    val profileImage: String
)

fun TaskUserResponseDTO.toDomainModel(): TaskUserResponseModel {
    return TaskUserResponseModel(
        userCode = userCode,
        username = username,
        profileImage = profileImage
    )
}