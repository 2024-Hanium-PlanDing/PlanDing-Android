package com.comst.presentation.main.group.detail

import androidx.compose.runtime.Immutable
import com.comst.domain.model.base.Schedule
import com.comst.domain.util.DateUtils
import com.comst.presentation.common.base.BaseEvent
import com.comst.presentation.common.base.BaseIntent
import com.comst.presentation.common.base.BaseSideEffect
import com.comst.presentation.common.base.UIState
import com.comst.presentation.model.group.GroupProfileUIModel
import java.time.LocalDate
import java.util.Date

class GroupDetailContract {
    @Immutable
    data class GroupDetailUIState(
        val groupProfile: GroupProfileUIModel = GroupProfileUIModel(
            id = 6678,
            name = "Marcia Finley",
            groupCode = "expetendis",
            description = "iusto",
            thumbnailUrl = "https://search.yahoo.com/search?p=doming",
            createdBy = "mnesarchum",
            false
        ),
        val selectLocalDate: LocalDate = LocalDate.now(),
        val selectUIDate: String = DateUtils.localDateToUIDate(selectLocalDate),
        val selectDay: String = DateUtils.getDayOfWeek(selectLocalDate),
        val selectedWeekdays: List<String> = DateUtils.getWeekDays(selectLocalDate),
        val selectWeekGroupScheduleEvents: List<Schedule> = emptyList(),
        val newScheduleEvents: List<Schedule> = emptyList(),
        val isBottomSheetVisible: Boolean = false,
        val isLoading: Boolean = false
    ) : UIState

    sealed class GroupDetailSideEffect : BaseSideEffect {

    }

    sealed class GroupDetailIntent : BaseIntent {
        object OpenBottomSheetClick: GroupDetailIntent()
        object CloseBottomSheetClick: GroupDetailIntent()

        data class SelectDate(val date: Date) : GroupDetailIntent()

    }


    sealed class GroupDetailEvent : BaseEvent {
        data class DateSelected(val date: LocalDate) : GroupDetailEvent()
    }
}