package com.comst.presentation.model.group

import com.comst.domain.model.groupTask.TaskResponseModel
import com.comst.domain.util.DateUtils

data class TaskUIModel(
    val id: Long,
    val scheduleId: Long,
    val plannerNumber: Int,
    val title: String,
    val content: String,
    val status: String,
    val deadline: String,
    val manager: TaskUserUIModel,
    val users: List<TaskUserUIModel>
)

fun TaskResponseModel.toTaskUIModel(): TaskUIModel {
    return TaskUIModel(
        id = id,
        scheduleId = scheduleId,
        plannerNumber = plannerNumber,
        title = title,
        content = content,
        status = status,
        deadline = DateUtils.formatDateTime(deadline),
        manager = manager.toTaskUserUIModel(),
        users = users.map { it.toTaskUserUIModel() }
    )
}