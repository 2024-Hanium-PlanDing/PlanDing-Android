package com.comst.presentation.main.schedule

import com.comst.domain.model.base.Schedule
import com.comst.domain.util.DateUtils
import com.comst.presentation.common.base.BaseEvent
import com.comst.presentation.common.base.BaseIntent
import com.comst.presentation.common.base.BaseSideEffect
import com.comst.presentation.common.base.UIState
import java.time.LocalDate
import java.util.Date

class ScheduleContract {

    data class ScheduleUIState(
        val selectLocalDate: LocalDate = LocalDate.now(),
        val selectUIDate: String = DateUtils.localDateToUIDate(selectLocalDate),
        val selectDay: String = DateUtils.getDayOfWeek(selectLocalDate),
        val selectedWeekdays: List<String> = DateUtils.getWeekDays(selectLocalDate),
        val todayPersonalScheduleEvents: List<Schedule> = emptyList(),
        val todayGroupScheduleEvents: List<Schedule> = emptyList(),
        val selectWeekScheduleEvents: List<Schedule> = emptyList(),
        val isBottomSheetVisible: Boolean = false,
        val isExpanded: Boolean = false,
        val isTodayScheduleVisible: Boolean = false,
        val isAddScheduleDialogVisible: Boolean = false,
        val isLoading: Boolean = false
    ): UIState

    sealed class ScheduleSideEffect : BaseSideEffect {
        data class ShowToast(val message: String) : ScheduleSideEffect()
    }

    sealed class ScheduleIntent : BaseIntent {
        object OpenBottomSheetClick : ScheduleIntent()
        object CloseBottomSheetClick : ScheduleIntent()
        data class SelectDate(val date: Date) : ScheduleIntent()
        object ToggleTodayScheduleVisibility : ScheduleIntent()
        object AddTodaySchedule : ScheduleIntent()
        object ShowAddScheduleDialog : ScheduleIntent()
        object HideAddScheduleDialog : ScheduleIntent()
    }

    sealed class ScheduleEvent : BaseEvent {
        data class DateSelected(val date: LocalDate) : ScheduleEvent()
        data class LoadFailure(val message: String) : ScheduleEvent()
    }
}