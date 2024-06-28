package com.comst.presentation.main.schedule

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.comst.domain.util.DateUtils
import com.comst.domain.usecase.commonSchedule.GetCommonScheduleTodayListUseCase
import com.comst.domain.usecase.commonSchedule.GetCommonScheduleWeekListUseCase
import com.comst.domain.usecase.personalSchedule.GetPersonalScheduleListUseCase
import com.comst.domain.util.onFailure
import com.comst.domain.util.onSuccess
import com.comst.presentation.common.base.BaseViewModel
import com.comst.presentation.main.schedule.ScheduleContract.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class ScheduleViewModel @Inject constructor(
    private val getCommonScheduleTodayListUseCase: GetCommonScheduleTodayListUseCase,
    private val getCommonScheduleWeekListUseCase: GetCommonScheduleWeekListUseCase,
    private val getPersonalScheduleListUseCase: GetPersonalScheduleListUseCase
) : BaseViewModel<ScheduleUIState, ScheduleUISideEffect, ScheduleUIEvent>(ScheduleUIState()){

    init {
        load()
    }

    override suspend fun handleEvent(event: ScheduleUIEvent) {
        when(event){
            is ScheduleUIEvent.OpenBottomSheetClick -> onOpenBottomSheet()
            is ScheduleUIEvent.CloseBottomSheetClick -> onCloseBottomSheet()
            is ScheduleUIEvent.SelectedDate -> onSelectedDate(event.date)
            is ScheduleUIEvent.ToggleTodayScheduleVisibility -> onToggleTextViewVisibility()
            is ScheduleUIEvent.AddTodaySchedule -> onAddTodaySchedule()
        }
    }

    fun load() = viewModelScope.launch{

        val weeklySchedulePeriod = DateUtils.getWeekStartAndEnd(currentState.selectLocalDate)
        getCommonScheduleWeekListUseCase(
            weeklySchedulePeriod
        ).onSuccess {
            setState {
                copy(
                    selectWeekScheduleEvents = it,
                )
            }
        }.onFailure { statusCode, message ->

        }

        val dailyPeriod = DateUtils.getDayStartAndEnd(currentState.selectLocalDate)
        getPersonalScheduleListUseCase(
            dailyPeriod
        ).onSuccess {
            setState {
                copy(
                    todayPersonalScheduleEvents = it,
                )
            }
        }.onFailure{ statusCode, message ->

        }

    }

    private fun onOpenBottomSheet() = viewModelScope.launch {
        setState {
            copy(isBottomSheetVisible = true)
        }
    }

    private fun onCloseBottomSheet() = viewModelScope.launch {
        setState {
            copy(isBottomSheetVisible = false)
        }
    }

    private fun onToggleTextViewVisibility() = viewModelScope.launch {
        setState {
            copy(isTodayScheduleVisible = !currentState.isTodayScheduleVisible)
        }
    }

    private fun onSelectedDate(date: Date) = viewModelScope.launch {
        val newSelectLocalDate = DateUtils.dateToLocalDate(date)
        val weeklySchedulePeriod = DateUtils.getWeekStartAndEnd(newSelectLocalDate)
        val dailyPeriod = DateUtils.getDayStartAndEnd(newSelectLocalDate)


        getCommonScheduleWeekListUseCase(
            weeklySchedulePeriod
        ).onSuccess {
            setState {
                copy(
                    selectLocalDate = newSelectLocalDate,
                    selectUIDate = DateUtils.localDateToUIDate(newSelectLocalDate),
                    selectDay = DateUtils.getDayOfWeek(newSelectLocalDate),
                    selectedWeekdays = DateUtils.getWeekDays(newSelectLocalDate),
                    selectWeekScheduleEvents = it,
                    isBottomSheetVisible = false,
                )
            }
        }.onFailure { statusCode, message ->

        }

        getPersonalScheduleListUseCase(
            dailyPeriod
        ).onSuccess {
            setState {
                copy(
                    todayPersonalScheduleEvents = it,
                )
            }
        }.onFailure{ statusCode, message ->

        }
    }

    private fun onAddTodaySchedule() = viewModelScope.launch {

    }

}