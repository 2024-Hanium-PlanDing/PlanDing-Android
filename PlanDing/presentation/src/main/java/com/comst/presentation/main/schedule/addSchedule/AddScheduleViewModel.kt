package com.comst.presentation.main.schedule.addSchedule

import com.comst.presentation.common.base.BaseViewModel
import com.comst.presentation.main.schedule.addSchedule.AddScheduleContract.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddScheduleViewModel @Inject constructor() :
    BaseViewModel<AddScheduleUIState, AddScheduleSideEffect, AddScheduleUIEvent>(
        AddScheduleUIState()
    ) {

    override suspend fun handleEvent(event: AddScheduleUIEvent) {
        when (event) {
            is AddScheduleUIEvent.DescriptionChange -> onDescriptionChange(event.description)
            is AddScheduleUIEvent.SelectedEndTime -> onSelectedEndTime(event.endTime)
            is AddScheduleUIEvent.SelectedStartTime -> onSelectedStartTime(event.startTime)
            is AddScheduleUIEvent.TitleChange -> onTitleChange(event.title)
        }
    }

    fun initialize(date: String) {
        setState {
            copy(date = date)
        }
    }

    private fun onTitleChange(title: String){
        setState {
            copy(title = title)
        }
    }

    private fun onDescriptionChange(description: String){
        setState {
            copy(description = description)
        }
    }

    private fun onSelectedStartTime(startTime: String){
        setState {
            copy(startTime = startTime)
        }
    }

    private fun onSelectedEndTime(endTime: String){
        setState {
            copy(endTime = endTime)
        }
    }

}
