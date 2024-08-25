package com.comst.presentation.main.group.detail.scheduleDetail

import com.comst.domain.model.base.Schedule
import com.comst.presentation.common.base.BaseViewModel
import com.comst.presentation.main.group.detail.scheduleDetail.ScheduleDetailContract.ScheduleDetailEvent
import com.comst.presentation.main.group.detail.scheduleDetail.ScheduleDetailContract.ScheduleDetailIntent
import com.comst.presentation.main.group.detail.scheduleDetail.ScheduleDetailContract.ScheduleDetailSideEffect
import com.comst.presentation.main.group.detail.scheduleDetail.ScheduleDetailContract.ScheduleDetailUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ScheduleDetailViewModel @Inject constructor(

): BaseViewModel<ScheduleDetailUIState, ScheduleDetailSideEffect, ScheduleDetailIntent, ScheduleDetailEvent>(ScheduleDetailUIState()){
    override fun handleIntent(intent: ScheduleDetailIntent) {

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
    }

}