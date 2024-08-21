package com.comst.presentation.main.schedule.addSchedule

import androidx.compose.runtime.Immutable
import com.comst.domain.model.base.Schedule
import com.comst.presentation.common.base.BaseEvent
import com.comst.presentation.common.base.BaseIntent
import com.comst.presentation.common.base.BaseSideEffect
import com.comst.presentation.common.base.UIState
import java.time.LocalDate

class AddPersonalScheduleContract {

    @Immutable
    data class AddPersonalScheduleUIState(
        val date: LocalDate = LocalDate.now(),
        val uiDate: String = "",
        val title: String = "",
        val content: String = "",
        val startTime: Int = 6,
        val endTime: Int = 6,
        val isLoading: Boolean = false,
    ) : UIState

    sealed class AddPersonalScheduleSideEffect : BaseSideEffect {
        data class SuccessCreatePersonalSchedule(val schedule: Schedule) : AddPersonalScheduleSideEffect()
    }

    sealed class AddPersonalScheduleIntent : BaseIntent {
        data class Initialize(val date: LocalDate): AddPersonalScheduleIntent()
        data class TitleChange(val title: String) : AddPersonalScheduleIntent()
        data class DescriptionChange(val description: String) : AddPersonalScheduleIntent()
        data class SelectedStartTime(val startTime: Int) : AddPersonalScheduleIntent()
        data class SelectedEndTime(val endTime: Int) : AddPersonalScheduleIntent()
        object CreatePersonalSchedule : AddPersonalScheduleIntent()
    }

    sealed class AddPersonalScheduleEvent : BaseEvent {
        data class CreationFailure(val message: String) : AddPersonalScheduleEvent()
    }
}