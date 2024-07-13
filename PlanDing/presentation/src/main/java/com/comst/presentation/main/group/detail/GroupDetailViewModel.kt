package com.comst.presentation.main.group.detail

import androidx.lifecycle.viewModelScope
import com.comst.domain.usecase.group.GetGroupInformationUseCase
import com.comst.domain.usecase.groupSchedule.GetGroupScheduleUseCase
import com.comst.domain.util.DateUtils
import com.comst.domain.util.onFailure
import com.comst.domain.util.onSuccess
import com.comst.presentation.common.base.BaseViewModel
import com.comst.presentation.main.group.detail.GroupDetailContract.*
import com.comst.presentation.model.group.toUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class GroupDetailViewModel @Inject constructor(
    private val getGroupInformationUseCase: GetGroupInformationUseCase,
    private val getGroupScheduleUseCase: GetGroupScheduleUseCase
) : BaseViewModel<GroupDetailUIState, GroupDetailSideEffect, GroupDetailIntent, GroupDetailEvent>(GroupDetailUIState()) {

    override fun handleIntent(intent: GroupDetailIntent) {
        when(intent){
            is GroupDetailIntent.OpenBottomSheetClick -> onOpenBottomSheet()
            is GroupDetailIntent.CloseBottomSheetClick -> onCloseBottomSheet()
            is GroupDetailIntent.SelectDate -> onSelectDate(intent.date)
        }
    }

    override fun handleEvent(event: GroupDetailEvent) {
        when (event){
            is GroupDetailEvent.DateSelected -> onDateSelected(event.date)
        }
    }

    fun initialize(groupCode: String) = viewModelScope.launch {
        setState { copy(isLoading = true) }
        getGroupInformationUseCase(groupCode).onSuccess { groupInformation ->
            getGroupScheduleUseCase(
                groupCode = groupInformation.groupCode,
                schedulePeriodModel = DateUtils.getWeekStartAndEnd(currentState.selectLocalDate)
            ).onSuccess { groupSchedules ->
                setState {
                    copy(
                        groupProfile =  groupInformation.toUIModel(),
                        selectWeekGroupScheduleEvents = groupSchedules
                    )
                }
            }.onFailure {

            }
        }.onFailure {

        }
        setState { copy(isLoading = false) }
    }

    private fun onOpenBottomSheet() {
        setState {
            copy(isBottomSheetVisible = true)
        }
    }

    private fun onCloseBottomSheet() {
        setState {
            copy(isBottomSheetVisible = false)
        }
    }

    private fun onSelectDate(date: Date) {
        val selectedLocalDate = DateUtils.dateToLocalDate(date)
        setState {
            copy(
                selectLocalDate = selectedLocalDate,
                isBottomSheetVisible = false
            )
        }
        setEvent(GroupDetailEvent.DateSelected(selectedLocalDate))
    }

    private fun onDateSelected(date: LocalDate) = viewModelScope.launch {
        val weeklySchedulePeriod = DateUtils.getWeekStartAndEnd(date)
        val dailyPeriod = DateUtils.getDayStartAndEnd(date)

        getGroupScheduleUseCase(
            groupCode = currentState.groupProfile.groupCode,
            schedulePeriodModel = weeklySchedulePeriod
        ).onSuccess {
            setState {
                copy(
                    selectLocalDate = date,
                    selectUIDate = DateUtils.localDateToUIDate(date),
                    selectDay = DateUtils.getDayOfWeek(date),
                    selectedWeekdays = DateUtils.getWeekDays(date)
                )
            }
        }.onFailure {

        }


    }

    override fun handleError(exception: Exception) {
        super.handleError(exception)
        setToastEffect(exception.message.orEmpty())
    }
}