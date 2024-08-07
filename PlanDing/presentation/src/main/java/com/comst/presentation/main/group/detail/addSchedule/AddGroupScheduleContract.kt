package com.comst.presentation.main.group.detail.addSchedule

import androidx.compose.runtime.Immutable
import com.comst.domain.model.base.Schedule
import com.comst.presentation.common.base.BaseEvent
import com.comst.presentation.common.base.BaseIntent
import com.comst.presentation.common.base.BaseSideEffect
import com.comst.presentation.common.base.UIState
import com.comst.presentation.model.group.GroupProfileUIModel
import com.comst.presentation.model.group.socket.SendCreateScheduleDTO
import java.time.LocalDate

class AddGroupScheduleContract {

    @Immutable
    data class AddGroupScheduleUIState(
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
        val date: LocalDate = LocalDate.now(),
        val uiDate: String = "",
        val title: String = "",
        val content: String = "",
        val startTime: Int = 6,
        val endTime: Int = 6,
        val isLoading: Boolean = false,
    ) : UIState

    sealed class AddGroupScheduleSideEffect : BaseSideEffect {
        data class SuccessCreateGroupSchedule(val schedule: SendCreateScheduleDTO) : AddGroupScheduleSideEffect()
    }

    sealed class AddGroupScheduleIntent : BaseIntent {
        data class TitleChange(val title: String) : AddGroupScheduleIntent()
        data class DescriptionChange(val description: String) : AddGroupScheduleIntent()
        data class SelectedStartTime(val startTime: Int) : AddGroupScheduleIntent()
        data class SelectedEndTime(val endTime: Int) : AddGroupScheduleIntent()
        object CreateGroupSchedule : AddGroupScheduleIntent()
    }

    sealed class AddGroupScheduleEvent : BaseEvent {
        data class CreationFailure(val message: String) : AddGroupScheduleEvent()
    }
}