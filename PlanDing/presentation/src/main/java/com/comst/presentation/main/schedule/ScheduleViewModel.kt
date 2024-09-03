package com.comst.presentation.main.schedule

import androidx.lifecycle.viewModelScope
import com.comst.domain.model.base.Schedule
import com.comst.domain.usecase.commonSchedule.GetCommonScheduleTodayListUseCase
import com.comst.domain.usecase.commonSchedule.GetCommonScheduleWeekListUseCase
import com.comst.domain.usecase.local.ClearUserDataUseCase
import com.comst.domain.usecase.personalSchedule.GetPersonalScheduleListUseCase
import com.comst.domain.util.DateUtils
import com.comst.domain.util.onException
import com.comst.domain.util.onFailure
import com.comst.domain.util.onSuccess
import com.comst.presentation.common.base.BaseViewModel
import com.comst.presentation.main.schedule.ScheduleContract.ScheduleEvent
import com.comst.presentation.main.schedule.ScheduleContract.ScheduleIntent
import com.comst.presentation.main.schedule.ScheduleContract.ScheduleSideEffect
import com.comst.presentation.main.schedule.ScheduleContract.ScheduleUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class ScheduleViewModel @Inject constructor(
    private val getCommonScheduleTodayListUseCase: GetCommonScheduleTodayListUseCase,
    private val getCommonScheduleWeekListUseCase: GetCommonScheduleWeekListUseCase,
    private val getPersonalScheduleListUseCase: GetPersonalScheduleListUseCase,
    private val clearUserDataUseCase: ClearUserDataUseCase
) : BaseViewModel<ScheduleUIState, ScheduleSideEffect, ScheduleIntent, ScheduleEvent>(ScheduleUIState()) {

    init {
        load()
    }

    override fun handleIntent(intent: ScheduleIntent) {
        when (intent) {
            is ScheduleIntent.OpenCalendarBottomSheet -> onOpenCalendarBottomSheet()
            is ScheduleIntent.CloseCalendarBottomSheet -> onCloseCalendarBottomSheet()
            is ScheduleIntent.SelectDate -> onSelectDate(intent.date)
            is ScheduleIntent.ToggleTodayScheduleVisibility -> onToggleTextViewVisibility()
            is ScheduleIntent.ShowAddScheduleDialog -> onShowAddScheduleDialog()
            is ScheduleIntent.HideAddScheduleDialog -> onHideAddScheduleDialog()
        }
    }

    override fun handleEvent(event: ScheduleEvent) {
        when (event) {
            is ScheduleEvent.DateSelected -> onDateSelected(event.date)
            is ScheduleEvent.LoadFailure -> setToastEffect(event.message)
            is ScheduleEvent.AddTodaySchedule -> onAddTodaySchedule(event.schedule)
        }
    }

    private fun load() {
        viewModelScope.launch {
            loadWeeklySchedule()
            loadDailySchedule()
        }
    }

    private fun loadWeeklySchedule() = viewModelScope.launch(apiExceptionHandler) {
        val weeklySchedulePeriod = DateUtils.getWeekStartAndEnd(currentState.selectLocalDate)
        getCommonScheduleWeekListUseCase(weeklySchedulePeriod)
            .onSuccess {
                setState {
                    copy(selectWeekScheduleList = it)
                }
            }.onFailure {

            }.onException { exception ->
                throw exception
            }
    }

    private fun loadDailySchedule() = viewModelScope.launch(apiExceptionHandler) {
        val dailyPeriod = DateUtils.getDayStartAndEnd(currentState.selectLocalDate)
        getPersonalScheduleListUseCase(dailyPeriod)
            .onSuccess {
                setState {
                    copy(todayPersonalScheduleList = it)
                }
            }.onFailure {

            }.onException { exception ->
                throw exception
            }
    }
    private fun onOpenCalendarBottomSheet() {
        setState {
            copy(isCalendarBottomSheetVisible = true)
        }
    }

    private fun onCloseCalendarBottomSheet() {
        setState {
            copy(isCalendarBottomSheetVisible = false)
        }
    }

    private fun onToggleTextViewVisibility() {
        setState {
            copy(isTodayScheduleVisible = !currentState.isTodayScheduleVisible)
        }
    }

    private fun onSelectDate(date: Date) {
        if (!canHandleClick(SELECT_DATE)) return
        val selectedLocalDate = DateUtils.dateToLocalDate(date)
        setState {
            copy(
                selectLocalDate = selectedLocalDate,
                isCalendarBottomSheetVisible = false
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

    private fun onDateSelected(date: LocalDate) = viewModelScope.launch(apiExceptionHandler) {
        val weeklySchedulePeriod = DateUtils.getWeekStartAndEnd(date)
        val dailyPeriod = DateUtils.getDayStartAndEnd(date)

        launch{
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
                }.onFailure {

                }.onException { exception ->
                    throw exception
                }
        }

        launch {
            getPersonalScheduleListUseCase(dailyPeriod)
                .onSuccess {
                    setState {
                        copy(todayPersonalScheduleList = it)
                    }
                }
                .onFailure {

                }.onException { exception ->
                    throw exception
                }
        }
    }

    companion object{
        const val SELECT_DATE = "selectDate"
    }
}