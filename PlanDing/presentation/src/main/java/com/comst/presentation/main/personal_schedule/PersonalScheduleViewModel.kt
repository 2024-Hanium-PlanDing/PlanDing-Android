package com.comst.presentation.main.personal_schedule

import androidx.lifecycle.ViewModel
import com.comst.domain.util.DateUtils
import com.comst.presentation.auth.LoginUIAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.annotation.concurrent.Immutable
import javax.inject.Inject

@HiltViewModel
class PersonalScheduleViewModel @Inject constructor(

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
            is PersonalScheduleUIAction.DateSelected -> onSelectDate(action.date)
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

    private fun onSelectDate(date: String) = intent {
        reduce {
            state.copy(selectDate = date, isBottomSheetVisible = false)
        }
    }

}

sealed class  PersonalScheduleUIAction{
    object OpenBottomSheet : PersonalScheduleUIAction()
    object CloseBottomSheet : PersonalScheduleUIAction()
    data class DateSelected(val date: String) : PersonalScheduleUIAction()
}

@Immutable
data class PersonalScheduleState(
    val selectDate : String = DateUtils.getCurrentDate("yyyy년 MM월 dd일"),
    val isBottomSheetVisible: Boolean = false
)

sealed interface PersonalScheduleSideEffect {

    data class Toast(val message: String) : PersonalScheduleSideEffect
}