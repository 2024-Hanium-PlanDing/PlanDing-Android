package com.comst.presentation.model.group.socket

import com.comst.domain.model.base.Schedule
import com.comst.domain.model.base.WebSocketType
import com.comst.domain.util.DateUtils
import com.comst.presentation.model.group.TaskUIModel
import com.comst.presentation.model.group.TaskUserUIModel
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ReceiveScheduleOrPlannerDTO(
    val content: String? = null,
    val endTime: Int? = null,
    val groupName: String? = null,
    val id: Long,
    val scheduleDate: String? = null,
    val startTime: Int? = null,
    val title: String? = null,
    val type: String? = null,
    val plannerNumber: Int? = null,
    val manager: TaskUserUIModel? = null,
    val scheduleId: Long? = null,
    val users: List<TaskUserUIModel>? = null,
    val status: String? = null,
    val deadline: String? = null,
    val action: String
)


fun ReceiveScheduleOrPlannerDTO.toDomainModel(): Schedule {
    val localDate = scheduleDate?.let { DateUtils.uiDateToLocalDate(it, "yyyy-MM-dd") }
    return Schedule(
        scheduleId = id,
        title = title.orEmpty(),
        content = content.orEmpty(),
        startTime = startTime ?: 0,
        endTime = endTime ?: 0,
        day = localDate?.let { DateUtils.getDayOfWeekUIFormat(it) }.orEmpty(),
        complete = false,
        groupName = groupName.orEmpty(),
        type = WebSocketType.GROUP
    )
}

fun ReceiveScheduleOrPlannerDTO.toTaskUIModel(): TaskUIModel {
    return TaskUIModel(
        id = id,
        plannerNumber = plannerNumber?:-1,
        title = title.orEmpty(),
        content = content.orEmpty(),
        status = status.orEmpty(),
        deadline = deadline.orEmpty(),
        manager = manager?: TaskUserUIModel(
            userCode = "voluptaria",
            username = "Earline Cannon",
            profileImage = "nihil"
        ),
        users = users?: emptyList()
    )
}