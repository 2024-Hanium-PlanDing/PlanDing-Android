package com.comst.presentation.main.schedule

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
            is ScheduleIntent.ShowAddScheduleDialog -> onShowAddScheduleDialog()
            is ScheduleIntent.HideAddScheduleDialog -> onHideAddScheduleDialog()
        }
    }

    override fun handleEvent(event: ScheduleEvent) {
        when (event) {
            is ScheduleEvent.DateSelected -> onDateSelected(event.date)
            is ScheduleEvent.LoadFailure -> onLoadFailure(event.message)
            is ScheduleEvent.AddTodaySchedule -> onAddTodaySchedule(event.schedule)
        }
    }

    private fun load() = viewModelScope.launch {
        val weeklySchedulePeriod = DateUtils.getWeekStartAndEnd(currentState.selectLocalDate)
        getCommonScheduleWeekListUseCase(weeklySchedulePeriod)
            .onSuccess {
                setState {
                    copy(selectWeekScheduleList = it)
                }
            }
            .onFailure {
            }

        val dailyPeriod = DateUtils.getDayStartAndEnd(currentState.selectLocalDate)
        getPersonalScheduleListUseCase(dailyPeriod)
            .onSuccess {
                setState {
                    copy(todayPersonalScheduleList = it)
                }
            }
            .onFailure {
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
            copy(
                selectLocalDate = selectedLocalDate,
                isBottomSheetVisible = false
            )
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

    private fun onAddTodaySchedule(schedule: Schedule) {
        setState {
            copy(
                todayPersonalScheduleList = currentState.todayPersonalScheduleList + schedule,
                selectWeekScheduleList = currentState.selectWeekScheduleList + schedule
            )
        }
    }

    private fun onDateSelected(date: LocalDate) = viewModelScope.launch {
        val weeklySchedulePeriod = DateUtils.getWeekStartAndEnd(date)
        val dailyPeriod = DateUtils.getDayStartAndEnd(date)

        getCommonScheduleWeekListUseCase(weeklySchedulePeriod)
            .onSuccess {
                setState {
                    copy(
                        selectLocalDate = date,
                        selectUIDate = DateUtils.localDateToUIDate(date),
                        selectDay = DateUtils.getDayOfWeek(date),
                        selectedWeekdays = DateUtils.getWeekDays(date),
                        selectWeekScheduleList = it
                    )
                }
            }
            .onFailure {

            }

        getPersonalScheduleListUseCase(dailyPeriod)
            .onSuccess {
                setState {
                    copy(todayPersonalScheduleList = it)
                }
            }
            .onFailure {

            }
    }

    private fun onLoadFailure(message: String) {
        setToastEffect(message)
    }

    override fun handleError(exception: Exception) {
        super.handleError(exception)
        setToastEffect(exception.message.orEmpty())
    }
}