package com.comst.presentation.main.personal_schedule

import android.util.Log
import androidx.lifecycle.ViewModel
import com.comst.domain.util.DateUtils
import com.comst.domain.model.base.ScheduleEvent
import com.comst.domain.usecase.commonSchedule.GetCommonScheduleTodayListUseCase
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
    private val getCommonScheduleTodayListUseCase: GetCommonScheduleTodayListUseCase
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

    fun onUIAction(action: PersonalScheduleUIAction) {
        when (action) {
            is PersonalScheduleUIAction.OpenBottomSheet -> onOpenBottomSheet()
            is PersonalScheduleUIAction.CloseBottomSheet -> onCloseBottomSheet()
            is PersonalScheduleUIAction.SelectedDate -> onSelectedDate(action.date)
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

    private fun onSelectedDate(date: Date) = intent {
        val newSelectLocalDate = DateUtils.dateToLocalDate(date)
        val todayScheduleEvents = getCommonScheduleTodayListUseCase().getOrThrow()
        reduce {
            state.copy(
                selectLocalDate = newSelectLocalDate,
                selectUIDate = DateUtils.localDateToUIDate(newSelectLocalDate),
                selectDay = DateUtils.getDayOfWeek(newSelectLocalDate),
                selectedWeekdays = DateUtils.getWeekDays(newSelectLocalDate),
                todayScheduleEvents = listOf(),
                selectWeekScheduleEvents = listOf(),
                isBottomSheetVisible = false,
            )
        }
    }
}

sealed class  PersonalScheduleUIAction{
    object OpenBottomSheet : PersonalScheduleUIAction()
    object CloseBottomSheet : PersonalScheduleUIAction()
    data class SelectedDate(val date: Date) : PersonalScheduleUIAction()
}

@Immutable
data class PersonalScheduleState(
    val selectLocalDate : LocalDate = LocalDate.now(),
    val selectUIDate : String = DateUtils.localDateToUIDate(selectLocalDate),
    val selectDay : String = DateUtils.getDayOfWeek(selectLocalDate),
    val selectedWeekdays : List<String> = DateUtils.getWeekDays(selectLocalDate),
    val todayScheduleEvents : List<ScheduleEvent> = emptyList(),
    val selectWeekScheduleEvents : List<ScheduleEvent> = emptyList(),
    val isBottomSheetVisible: Boolean = false
)

sealed interface PersonalScheduleSideEffect {
    data class Toast(val message: String) : PersonalScheduleSideEffect
}