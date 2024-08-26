package com.comst.data.model.groupTask

import com.comst.domain.model.groupTask.GroupTaskResponseModel
import com.google.gson.annotations.SerializedName

data class GroupTaskResponseDTO(
    @SerializedName("planner")
    val task: List<TaskResponseDTO>,
    val scheduleId: Long,
)

fun GroupTaskResponseDTO.toDomainModel(): GroupTaskResponseModel {
    return GroupTaskResponseModel(
        task = task.map { it.toDomainModel() },
        scheduleId = scheduleId
    )
}