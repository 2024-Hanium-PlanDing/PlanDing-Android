package com.comst.presentation.main.group.detail.scheduleDetail

import androidx.compose.runtime.Immutable
import com.comst.domain.model.base.Schedule
import com.comst.domain.model.base.ScheduleType
import com.comst.presentation.common.base.BaseEvent
import com.comst.presentation.common.base.BaseIntent
import com.comst.presentation.common.base.BaseSideEffect
import com.comst.presentation.common.base.UIState

class ScheduleDetailContract {

    @Immutable
    data class ScheduleDetailUIState(
        val groupCode: String = "",
        val schedule: Schedule = Schedule(
            scheduleId = 5852,
            title = "affert",
            content = "civibus",
            startTime = 4559,
            endTime = 2723,
            day = "phasellus",
            complete = false,
            groupName = null,
            type = ScheduleType.GROUP
        ),
    ): UIState

    sealed class ScheduleDetailSideEffect : BaseSideEffect {

    }

    sealed class ScheduleDetailIntent: BaseIntent {
        data class Initialize(val groupCode: String, val scheduleId: Long): ScheduleDetailIntent()
    }

    sealed class ScheduleDetailEvent : BaseEvent {

    }
}