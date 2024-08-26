package com.comst.presentation.main.group.detail.scheduleDetail

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.comst.domain.model.base.Schedule
import com.comst.domain.usecase.groupTask.GetTaskListOfScheduleUseCase
import com.comst.domain.util.onError
import com.comst.domain.util.onException
import com.comst.domain.util.onFailure
import com.comst.domain.util.onSuccess
import com.comst.presentation.common.base.BaseViewModel
import com.comst.presentation.main.group.detail.scheduleDetail.ScheduleDetailContract.ScheduleDetailEvent
import com.comst.presentation.main.group.detail.scheduleDetail.ScheduleDetailContract.ScheduleDetailIntent
import com.comst.presentation.main.group.detail.scheduleDetail.ScheduleDetailContract.ScheduleDetailSideEffect
import com.comst.presentation.main.group.detail.scheduleDetail.ScheduleDetailContract.ScheduleDetailUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScheduleDetailViewModel @Inject constructor(
    private val getTaskListOfScheduleUseCase: GetTaskListOfScheduleUseCase
): BaseViewModel<ScheduleDetailUIState, ScheduleDetailSideEffect, ScheduleDetailIntent, ScheduleDetailEvent>(ScheduleDetailUIState()){
    override fun handleIntent(intent: ScheduleDetailIntent) {
        when(intent){
            is ScheduleDetailIntent.Initialize -> initialize(groupCode = intent.groupCode, schedule = intent.schedule)
            is ScheduleDetailIntent.SelectTaskStatusOption -> onSelectTaskStatusOption(intent.option)
        }
    }

    override fun handleEvent(event: ScheduleDetailEvent) {

    }

    fun initialize(groupCode: String, schedule: Schedule){
        setState {
            copy(
                groupCode = groupCode,
                schedule = schedule
            )
        }

        viewModelScope.launch {
            getTaskListOfScheduleUseCase(
                groupCode = currentState.groupCode,
                scheduleId = schedule.scheduleId
            ).onSuccess {
                
            }.onFailure {

            }
        }
    }

    private fun onSelectTaskStatusOption(option: ScheduleDetailContract.TaskStatus){
        setState {
            copy(
                selectedOption = option
            )
        }
    }

}