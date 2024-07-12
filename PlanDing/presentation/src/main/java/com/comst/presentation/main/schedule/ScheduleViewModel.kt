package com.comst.presentation.main.schedule

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.comst.domain.model.base.Schedule
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
import java.time.LocalDate
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class ScheduleViewModel @Inject constructor(
    private val getCommonScheduleTodayListUseCase: GetCommonScheduleTodayListUseCase,
    private val getCommonScheduleWeekListUseCase: GetCommonScheduleWeekListUseCase,
    private val getPersonalScheduleListUseCase: GetPersonalScheduleListUseCase
) : BaseViewModel<ScheduleUIState, ScheduleSideEffect, ScheduleIntent, ScheduleEvent>(ScheduleUIState()) {

    init {
        load()
    }

    override fun handleIntent(intent: ScheduleIntent) {
        when (intent) {
            is ScheduleIntent.OpenBottomSheetClick -> onOpenBottomSheet()
            is ScheduleIntent.CloseBottomSheetClick -> onCloseBottomSheet()
            is ScheduleIntent.SelectDate -> onSelectDate(intent.date)
            is ScheduleIntent.ToggleTodayScheduleVisibility -> onToggleTextViewVisibility()
            is ScheduleIntent.AddTodaySchedule -> onAddTodaySchedule()
            is ScheduleIntent.ShowAddScheduleDialog -> onShowAddScheduleDialog()
            is ScheduleIntent.HideAddScheduleDialog -> onHideAddScheduleDialog()
        }
    }

    override fun handleEvent(event: ScheduleEvent) {
        when (event) {
            is ScheduleEvent.DateSelected -> onDateSelected(event.date)
            is ScheduleEvent.LoadFailure -> onLoadFailure(event.message)
        }
    }

    private fun load() = viewModelScope.launch {
        val weeklySchedulePeriod = DateUtils.getWeekStartAndEnd(currentState.selectLocalDate)
        getCommonScheduleWeekListUseCase(weeklySchedulePeriod)
            .onSuccess {
                setState {
                    copy(selectWeekScheduleEvents = it)
                }
            }
            .onFailure {
            }

        val dailyPeriod = DateUtils.getDayStartAndEnd(currentState.selectLocalDate)
        getPersonalScheduleListUseCase(dailyPeriod)
            .onSuccess {
                setState {
                    copy(todayPersonalScheduleEvents = it)
                }
            }
            .onFailure {
            }
    }

    fun addSchedule(scheduleEvent: Schedule) {
        setState {
            copy(
                todayPersonalScheduleEvents = currentState.todayPersonalScheduleEvents + scheduleEvent,
                selectWeekScheduleEvents = currentState.selectWeekScheduleEvents + scheduleEvent
            )
        }
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

    private fun onToggleTextViewVisibility() {
        setState {
            copy(isTodayScheduleVisible = !currentState.isTodayScheduleVisible)
        }
    }

    private fun onSelectDate(date: Date) {
        val selectedLocalDate = DateUtils.dateToLocalDate(date)
        setState {
            copy(selectLocalDate = selectedLocalDate)
        }
        setEvent(ScheduleEvent.DateSelected(selectedLocalDate))
    }

    private fun onShowAddScheduleDialog() {
        setState {
            copy(isAddScheduleDialogVisible = true)
        }
    }

    private fun onHideAddScheduleDialog() {
        setState {
            copy(isAddScheduleDialogVisible = false)
        }
    }

    private fun onAddTodaySchedule() {
        // Add schedule logic
    }

    private fun onDateSelected(date: LocalDate) = viewModelScope.launch {
        val newSelectLocalDate = date
        val weeklySchedulePeriod = DateUtils.getWeekStartAndEnd(newSelectLocalDate)
        val dailyPeriod = DateUtils.getDayStartAndEnd(newSelectLocalDate)

        getCommonScheduleWeekListUseCase(weeklySchedulePeriod)
            .onSuccess {
                setState {
                    copy(
                        selectLocalDate = newSelectLocalDate,
                        selectUIDate = DateUtils.localDateToUIDate(newSelectLocalDate),
                        selectDay = DateUtils.getDayOfWeek(newSelectLocalDate),
                        selectedWeekdays = DateUtils.getWeekDays(newSelectLocalDate),
                        selectWeekScheduleEvents = it,
                        isBottomSheetVisible = false
                    )
                }
            }
            .onFailure {

            }

        getPersonalScheduleListUseCase(dailyPeriod)
            .onSuccess {
                setState {
                    copy(todayPersonalScheduleEvents = it)
                }
            }
            .onFailure {

            }
    }

    private fun onLoadFailure(message: String) {
        setEffect(ScheduleSideEffect.ShowToast(message))
    }

    override fun handleError(exception: Exception) {
        super.handleError(exception)
        setEffect(ScheduleSideEffect.ShowToast(exception.message.orEmpty()))
    }
}