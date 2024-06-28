package com.comst.presentation.main.schedule

import com.comst.domain.model.base.ScheduleEvent
import com.comst.domain.util.DateUtils
import com.comst.presentation.common.base.UIEvent
import com.comst.presentation.common.base.UISideEffect
import com.comst.presentation.common.base.UIState
import java.time.LocalDate
import java.util.Date

class ScheduleContract {

    data class ScheduleUIState(
        val selectLocalDate : LocalDate = LocalDate.now(),
        val selectUIDate : String = DateUtils.localDateToUIDate(selectLocalDate),
        val selectDay : String = DateUtils.getDayOfWeek(selectLocalDate),
        val selectedWeekdays : List<String> = DateUtils.getWeekDays(selectLocalDate),
        val todayPersonalScheduleEvents : List<ScheduleEvent> = emptyList(),
        val todayGroupScheduleEvents : List<ScheduleEvent> = emptyList(),
        val selectWeekScheduleEvents : List<ScheduleEvent> = emptyList(),
        val isBottomSheetVisible: Boolean = false,
        val isExpanded: Boolean = false,
        val isTodayScheduleVisible: Boolean = false,
        val isLoading: Boolean = false
    ): UIState

    sealed class ScheduleUISideEffect: UISideEffect {
        data class ShowToast(val message: String): ScheduleUISideEffect()

    }

    sealed class ScheduleUIEvent: UIEvent {
        object OpenBottomSheetClick : ScheduleUIEvent()
        object CloseBottomSheetClick : ScheduleUIEvent()
        data class SelectedDate(val date: Date) : ScheduleUIEvent()
        object ToggleTodayScheduleVisibility : ScheduleUIEvent()
        object AddTodaySchedule : ScheduleUIEvent()
    }
}