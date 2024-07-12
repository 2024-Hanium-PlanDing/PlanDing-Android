package com.comst.presentation.main.schedule.addSchedule

import androidx.compose.runtime.Immutable
import com.comst.domain.model.base.Schedule
import com.comst.presentation.common.base.BaseEvent
import com.comst.presentation.common.base.BaseIntent
import com.comst.presentation.common.base.BaseSideEffect
import com.comst.presentation.common.base.UIState
import java.time.LocalDate

class AddScheduleContract {

    @Immutable
    data class AddScheduleUIState(
        val date: LocalDate = LocalDate.now(),
        val uiDate: String = "",
        val title: String = "",
        val content: String = "",
        val startTime: Int = 6,
        val endTime: Int = 6,
        val isLoading: Boolean = false,
    ) : UIState

    sealed class AddScheduleSideEffect : BaseSideEffect {
        data class SuccessCreateSchedule(val schedule: Schedule) : AddScheduleSideEffect()
    }

    sealed class AddScheduleIntent : BaseIntent {
        data class TitleChange(val title: String) : AddScheduleIntent()
        data class DescriptionChange(val description: String) : AddScheduleIntent()
        data class SelectedStartTime(val startTime: Int) : AddScheduleIntent()
        data class SelectedEndTime(val endTime: Int) : AddScheduleIntent()
        object CreateSchedule : AddScheduleIntent()
    }

    sealed class AddScheduleEvent : BaseEvent {
        data class CreationFailure(val message: String) : AddScheduleEvent()
    }
}