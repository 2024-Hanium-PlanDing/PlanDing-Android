package com.comst.presentation.model.group

import com.comst.domain.model.groupTask.TaskUserResponseModel

data class TaskUserUIModel(
    val userCode: String,
    val username: String,
    val profileImage: String
)


fun TaskUserResponseModel.toTaskUserUIModel(): TaskUserUIModel{
    return TaskUserUIModel(
        userCode = userCode,
        username = username,
        profileImage = profileImage
    )
}