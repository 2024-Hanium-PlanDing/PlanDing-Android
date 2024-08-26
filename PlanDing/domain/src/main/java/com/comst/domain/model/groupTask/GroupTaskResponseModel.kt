package com.comst.domain.model.groupTask

data class GroupTaskResponseModel(
    val task: List<TaskResponseModel>,
    val scheduleId: Long,
)
