package com.comst.presentation.main.schedule.addSchedule

import androidx.compose.runtime.Immutable
import com.comst.domain.model.base.ScheduleEvent
import com.comst.presentation.common.base.UIEvent
import com.comst.presentation.common.base.UISideEffect
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

    sealed class AddScheduleSideEffect : UISideEffect{
        data class ShowToast(val message: String): AddScheduleSideEffect()
        data class SuccessCreateSchedule(val scheduleEvent: ScheduleEvent): AddScheduleSideEffect()
    }

    sealed class AddScheduleUIEvent : UIEvent {
        data class TitleChange(val title: String) : AddScheduleUIEvent()
        data class DescriptionChange(val description: String) : AddScheduleUIEvent()
        data class SelectedStartTime(val startTime: Int) : AddScheduleUIEvent()
        data class SelectedEndTime(val endTime: Int) : AddScheduleUIEvent()
        object CreateSchedule: AddScheduleUIEvent()
    }
}