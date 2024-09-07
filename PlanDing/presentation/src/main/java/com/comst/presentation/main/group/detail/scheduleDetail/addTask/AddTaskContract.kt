package com.comst.presentation.main.group.detail.scheduleDetail.addTask

import androidx.compose.runtime.Immutable
import com.comst.domain.model.groupSchedule.GroupScheduleResponseModel
import com.comst.domain.util.DateUtils
import com.comst.presentation.common.base.BaseEvent
import com.comst.presentation.common.base.BaseIntent
import com.comst.presentation.common.base.BaseSideEffect
import com.comst.presentation.common.base.UIState
import com.comst.presentation.model.group.TaskUserUIModel

class AddTaskContract {

    @Immutable
    data class AddTaskUIState(
        val groupCode: String = "",
        val scheduleId: Long = -1,
        val userCode: String = "",
        val dueTime: Int = 6,
        val schedule: GroupScheduleResponseModel = GroupScheduleResponseModel(
            id = 4357,
            title = "facilisi",
            content = "dicat",
            scheduleDate = DateUtils.getCurrentDate(),
            startTime = 9738,
            endTime = 4338,
            day = "duis",
            type = "mea",
            groupName = "Christian Rowe",
            userScheduleAttendances = listOf()
        ),
        val groupMember: List<TaskUserUIModel> = listOf(),
        val taskTitle: String = "",
        val taskContent: String = "",
        val deadline: String = "",
        val isGroupMemberListVisible: Boolean = false
    ): UIState

    sealed class AddTaskSideEffect: BaseSideEffect{

    }

    sealed class AddTaskIntent: BaseIntent{
        data class Initialize(val groupCode: String, val scheduleId: Long): AddTaskIntent()
        data class TaskTitleChange(val taskTitle: String): AddTaskIntent()
        data class TaskContentChange(val taskContent: String): AddTaskIntent()
        object ToggleGroupMemberListVisibility: AddTaskIntent()
    }

    sealed class AddTaskEvent: BaseEvent{

    }
}