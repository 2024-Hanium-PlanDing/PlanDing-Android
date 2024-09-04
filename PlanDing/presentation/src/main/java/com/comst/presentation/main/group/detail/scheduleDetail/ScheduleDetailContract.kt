package com.comst.presentation.main.group.detail.scheduleDetail

import androidx.compose.runtime.Immutable
import com.comst.domain.model.base.Schedule
import com.comst.domain.model.base.ScheduleType
import com.comst.presentation.common.base.BaseEvent
import com.comst.presentation.common.base.BaseIntent
import com.comst.presentation.common.base.BaseSideEffect
import com.comst.presentation.common.base.UIState
import com.comst.presentation.common.util.UniqueList
import com.comst.presentation.model.group.TaskUIModel

class ScheduleDetailContract {

    @Immutable
    data class ScheduleDetailUIState(
        val userCode: String = "",
        val groupCode: String = "",
        val schedule: Schedule = Schedule(
            scheduleId = 5852,
            title = "affert",
            content = "civibusaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa\nadasddddddddddddddwadddddddddd\naaaaaaaaaaaaaaaaaaaaaaaaaaaaxxxxxxxxxxxwwwwwwww\nzzzzzzzzzzzzzzzzzzzzzzzzzzzzzwwwwwwwwwwwwwwwqqqqqqq\nzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz",
            startTime = 4559,
            endTime = 2723,
            day = "phasellus",
            complete = false,
            groupName = null,
        ),
        val taskOriginalList: UniqueList<TaskUIModel, Long> = UniqueList({ it.id }),
        val newTaskList: UniqueList<TaskUIModel, Long> = UniqueList({ it.id }),
        val selectedOption: TaskStatus = TaskStatus.TODO
    ): UIState

    sealed class ScheduleDetailSideEffect : BaseSideEffect {

    }

    sealed class ScheduleDetailIntent: BaseIntent {
        data class Initialize(val groupCode: String, val schedule: Schedule): ScheduleDetailIntent()
        data class SelectTaskStatusOption(val option: TaskStatus) : ScheduleDetailIntent()
    }

    sealed class ScheduleDetailEvent : BaseEvent {

    }

    enum class TaskStatus(val displayName: String) {
        TODO("대기"),
        IN_PROGRESS("진행"),
        DONE("완료")
    }
}