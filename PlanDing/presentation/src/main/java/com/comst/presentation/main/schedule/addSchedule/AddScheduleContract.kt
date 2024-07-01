package com.comst.presentation.main.schedule.addSchedule

import com.comst.presentation.common.base.UIEvent
import com.comst.presentation.common.base.UISideEffect
import com.comst.presentation.common.base.UIState

class AddScheduleContract {

    data class AddScheduleUIState(
        val date: String = "",
        val title: String = "",
        val description: String = "",
        val startTime: String = "",
        val endTime: String = "",
        val isLoading: Boolean = false,
    ) : UIState

    sealed class AddScheduleSideEffect : UISideEffect{
        data class ShowToast(val message: String): AddScheduleSideEffect()
    }

    sealed class AddScheduleUIEvent : UIEvent {
        data class TitleChange(val title: String) : AddScheduleUIEvent()
        data class DescriptionChange(val description: String) : AddScheduleUIEvent()
        data class SelectedStartTime(val startTime: String) : AddScheduleUIEvent()
        data class SelectedEndTime(val endTime: String) : AddScheduleUIEvent()
    }
}