package com.comst.presentation.main.personal_schedule

import android.util.Log
import androidx.lifecycle.ViewModel
import com.comst.domain.util.DateUtils
import com.comst.domain.model.base.ScheduleEvent
import com.comst.domain.model.base.ScheduleType
import com.comst.domain.usecase.commonSchedule.GetCommonScheduleTodayListUseCase
import com.comst.domain.usecase.commonSchedule.GetCommonScheduleWeekListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import java.time.LocalDate
import java.util.Date
import javax.annotation.concurrent.Immutable
import javax.inject.Inject

@HiltViewModel
class PersonalScheduleViewModel @Inject constructor(
    private val getCommonScheduleTodayListUseCase: GetCommonScheduleTodayListUseCase,
    private val getCommonScheduleWeekListUseCase: GetCommonScheduleWeekListUseCase,
) : ViewModel(), ContainerHost<PersonalScheduleState, PersonalScheduleSideEffect>{

    override val container: Container<PersonalScheduleState, PersonalScheduleSideEffect> = container(
        initialState = PersonalScheduleState(),
        buildSettings = {
            this.exceptionHandler = CoroutineExceptionHandler { _, throwable ->
                intent {
                    postSideEffect(PersonalScheduleSideEffect.Toast(throwable.message.orEmpty()))
                }
            }
        }
    )

    init {
        load()
    }

    fun load() = intent{

        val todayScheduleEvents = getCommonScheduleTodayListUseCase().getOrThrow()
        val startDateAndEndDate = DateUtils.getWeekStartAndEnd(state.selectLocalDate)
        val weekScheduleEvents = getCommonScheduleWeekListUseCase(
            startDateAndEndDate.first,
            startDateAndEndDate.second
        ).getOrThrow()

        reduce {
            state.copy(
                todayScheduleEvents = todayScheduleEvents,
                selectWeekScheduleEvents = weekScheduleEvents,
            )
        }
    }

    fun onUIAction(action: PersonalScheduleUIAction) {
        when (action) {
            is PersonalScheduleUIAction.OpenBottomSheet -> onOpenBottomSheet()
            is PersonalScheduleUIAction.CloseBottomSheet -> onCloseBottomSheet()
            is PersonalScheduleUIAction.SelectedDate -> onSelectedDate(action.date)
            is PersonalScheduleUIAction.ToggleTodayScheduleVisibility -> onToggleTextViewVisibility()
            is PersonalScheduleUIAction.AddTodaySchedule -> onAddTodaySchedule()
        }
    }

    private fun onOpenBottomSheet() = intent {
        reduce {
            state.copy(isBottomSheetVisible = true)
        }
    }

    private fun onCloseBottomSheet() = intent {
        reduce {
            state.copy(isBottomSheetVisible = false)
        }
    }

    private fun onToggleTextViewVisibility() = intent {
        reduce {
            state.copy(isTodayScheduleVisible = !state.isTodayScheduleVisible)
        }
    }

    private fun onSelectedDate(date: Date) = intent {
        val newSelectLocalDate = DateUtils.dateToLocalDate(date)
        val startDateAndEndDate = DateUtils.getWeekStartAndEnd(newSelectLocalDate)

        val weekScheduleEvents = getCommonScheduleWeekListUseCase(
            startDateAndEndDate.first,
            startDateAndEndDate.second
        ).getOrThrow()
        reduce {
            state.copy(
                selectLocalDate = newSelectLocalDate,
                selectUIDate = DateUtils.localDateToUIDate(newSelectLocalDate),
                selectDay = DateUtils.getDayOfWeek(newSelectLocalDate),
                selectedWeekdays = DateUtils.getWeekDays(newSelectLocalDate),
                selectWeekScheduleEvents = weekScheduleEvents,
                todayScheduleEvents = listOf(),
                isBottomSheetVisible = false,
            )
        }
    }

    private fun onAddTodaySchedule() = intent {
        val aa = mutableListOf<ScheduleEvent>()
        for (i in 6..12){
            aa.add(
                ScheduleEvent(
                    scheduleId = i.toLong(),
                    title = "propriae + $i",
                    content = "te",
                    startTime = i,
                    endTime = i+2,
                    day = "dapibus",
                    complete = i%2==1,
                    groupName = null,
                    type = ScheduleType.GROUP

                )
            )
        }

        reduce {
            state.copy(
                todayScheduleEvents = aa
            )
        }
    }
}

sealed class  PersonalScheduleUIAction{
    object OpenBottomSheet : PersonalScheduleUIAction()
    object CloseBottomSheet : PersonalScheduleUIAction()
    data class SelectedDate(val date: Date) : PersonalScheduleUIAction()
    object ToggleTodayScheduleVisibility : PersonalScheduleUIAction()
    object AddTodaySchedule : PersonalScheduleUIAction()
}

@Immutable
data class PersonalScheduleState(
    val selectLocalDate : LocalDate = LocalDate.now(),
    val selectUIDate : String = DateUtils.localDateToUIDate(selectLocalDate),
    val selectDay : String = DateUtils.getDayOfWeek(selectLocalDate),
    val selectedWeekdays : List<String> = DateUtils.getWeekDays(selectLocalDate),
    val todayScheduleEvents : List<ScheduleEvent> = emptyList(),
    val selectWeekScheduleEvents : List<ScheduleEvent> = emptyList(),
    val isBottomSheetVisible: Boolean = false,
    val isExpanded: Boolean = false,
    val isTodayScheduleVisible: Boolean = false
)

sealed interface PersonalScheduleSideEffect {
    data class Toast(val message: String) : PersonalScheduleSideEffect
}