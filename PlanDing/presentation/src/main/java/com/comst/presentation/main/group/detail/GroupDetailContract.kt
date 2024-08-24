package com.comst.presentation.main.group.detail

import androidx.compose.runtime.Immutable
import com.comst.domain.model.base.Schedule
import com.comst.domain.model.chat.ChatMessageModel
import com.comst.domain.model.group.GroupUserInformationModel
import com.comst.domain.util.DateUtils
import com.comst.presentation.common.base.BaseEvent
import com.comst.presentation.common.base.BaseIntent
import com.comst.presentation.common.base.BaseSideEffect
import com.comst.presentation.common.base.UIState
import com.comst.presentation.common.util.UniqueList
import com.comst.presentation.model.group.GroupProfileUIModel
import com.comst.presentation.model.group.socket.SendCreateScheduleDTO
import java.time.LocalDate
import java.util.Date

class GroupDetailContract {
    @Immutable
    data class GroupDetailUIState(
        val userCode: String = "",
        val groupProfile: GroupProfileUIModel = GroupProfileUIModel(
            id = 6678,
            name = "Marcia Finley",
            groupCode = "expetendis",
            description = "iusto",
            thumbnailUrl = "https://search.yahoo.com/search?p=doming",
            createdBy = "mnesarchum",
            isFavorite = false,
            isAlarm = false,
            isGroupAdmin = false
        ),
        val groupMember: List<GroupUserInformationModel> = listOf(),
        val selectLocalDate: LocalDate = LocalDate.now(),
        val selectUIDate: String = DateUtils.localDateToUIDate(selectLocalDate),
        val selectDay: String = DateUtils.getDayOfWeek(selectLocalDate),
        val selectedWeekdays: List<String> = DateUtils.getWeekDays(selectLocalDate),
        val selectWeekGroupScheduleOriginalList: UniqueList<Schedule, Long> = UniqueList({ it.scheduleId }),
        val chatOriginalList: UniqueList<ChatMessageModel, Long> = UniqueList({ it.id }),
        val newScheduleList: UniqueList<Schedule, Long> = UniqueList({ it.scheduleId }),
        val newChatList: UniqueList<ChatMessageModel, Long> = UniqueList({ it.id }),
        val isBottomSheetVisible: Boolean = false,
        val isBarChartView: Boolean = true,
        val isLoading: Boolean = false,
        val selectedDayIndex: Int = selectedWeekdays.indexOfFirst {
            it.firstOrNull() == selectDay.firstOrNull()
        },
        val isAddScheduleDialogVisible: Boolean = false,
        val isAddGroupMemberDialogVisible: Boolean = false,
        val currentPage: Int = 0,
        val chat: String = "",
        val selectScheduleId: Long = -1,
        val isScheduleDetailDialogVisible: Boolean = false,
    ) : UIState

    sealed class GroupDetailSideEffect : BaseSideEffect {

    }

    sealed class GroupDetailIntent : BaseIntent {
        object OpenBottomSheetClick : GroupDetailIntent()
        object CloseBottomSheetClick : GroupDetailIntent()

        object ToggleView : GroupDetailIntent()
        data class SelectDate(val date: Date) : GroupDetailIntent()
        data class SelectDay(val index: Int): GroupDetailIntent()
        object ShowAddScheduleDialog : GroupDetailIntent()
        object HideAddScheduleDialog : GroupDetailIntent()
        object ShowAddGroupMemberDialog : GroupDetailIntent()
        object HideAddGroupMemberDialog : GroupDetailIntent()
        data class ChangePage(val pageIndex: Int) : GroupDetailIntent()
        data class ChatChange(val chat: String) : GroupDetailIntent()
        object SendChat: GroupDetailIntent()
        data class CreateSchedule(val newSchedule: SendCreateScheduleDTO): GroupDetailIntent()
        data class ShowScheduleDetailDialog(val scheduleId: Long): GroupDetailIntent()
        object HideScheduleDetailDialog: GroupDetailIntent()
    }


    sealed class GroupDetailEvent : BaseEvent {
        data class DateSelected(val date: LocalDate) : GroupDetailEvent()
        data class AddGroupSchedule(val schedule: Schedule) : GroupDetailEvent()
    }
}