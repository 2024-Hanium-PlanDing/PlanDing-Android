package com.comst.presentation.model.group.socket

import com.comst.presentation.model.group.TaskUIModel
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ReceiveTaskDTO(
    val planner: TaskUIModel,
    val type: String,
    val action: String
)