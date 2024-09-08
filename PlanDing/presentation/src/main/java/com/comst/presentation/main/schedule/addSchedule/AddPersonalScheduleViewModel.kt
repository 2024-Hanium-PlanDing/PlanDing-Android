package com.comst.presentation.main.schedule.addSchedule

import androidx.lifecycle.viewModelScope
import com.comst.domain.model.base.ScheduleModel
import com.comst.domain.usecase.personalSchedule.PostPersonalScheduleUseCase
import com.comst.domain.util.DateUtils
import com.comst.domain.util.onException
import com.comst.domain.util.onFailure
import com.comst.domain.util.onSuccess
import com.comst.presentation.common.base.BaseViewModel
import com.comst.presentation.main.schedule.addSchedule.AddPersonalScheduleContract.AddPersonalScheduleEvent
import com.comst.presentation.main.schedule.addSchedule.AddPersonalScheduleContract.AddPersonalScheduleIntent
import com.comst.presentation.main.schedule.addSchedule.AddPersonalScheduleContract.AddPersonalScheduleSideEffect
import com.comst.presentation.main.schedule.addSchedule.AddPersonalScheduleContract.AddPersonalScheduleUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class AddPersonalScheduleViewModel @Inject constructor(
    private val postPersonalScheduleUseCase: PostPersonalScheduleUseCase
) : BaseViewModel<AddPersonalScheduleUIState, AddPersonalScheduleSideEffect, AddPersonalScheduleIntent, AddPersonalScheduleEvent>(AddPersonalScheduleUIState()) {

    override fun handleIntent(intent: AddPersonalScheduleIntent) {
        when (intent) {
            is AddPersonalScheduleIntent.Initialize -> initialize(intent.date)
            is AddPersonalScheduleIntent.DescriptionChange -> onDescriptionChange(intent.description)
            is AddPersonalScheduleIntent.SelectedEndTime -> onSelectedEndTime(intent.endTime)
            is AddPersonalScheduleIntent.SelectedStartTime -> onSelectedStartTime(intent.startTime)
            is AddPersonalScheduleIntent.TitleChange -> onTitleChange(intent.title)
            is AddPersonalScheduleIntent.CreatePersonalSchedule -> onCreateScheduleClick()
        }
    }

    override fun handleEvent(event: AddPersonalScheduleEvent) {
        when (event) {
            is AddPersonalScheduleEvent.CreationFailure -> onCreationFailure(event.message)
        }
    }

    fun initialize(date: LocalDate) {
        setState {
            copy(
                date = date,
                uiDate = DateUtils.localDateToUIDate(date),
                title = "",
                content = "",
                startTime = 6,
                endTime = 6,
                isLoading = false
            )
        }
    }

    private fun onTitleChange(title: String) {
        setState {
            copy(title = title)
        }
    }

    private fun onDescriptionChange(description: String) {
        setState {
            copy(content = description)
        }
    }

    private fun onSelectedStartTime(startTime: Int) {
        setState {
            copy(startTime = startTime)
        }
    }

    private fun onSelectedEndTime(endTime: Int) {
        setState {
            copy(endTime = endTime)
        }
    }

    private fun onCreateScheduleClick() = viewModelScope.launch(apiExceptionHandler) {
        if (currentState.title.isEmpty() || currentState.content.isEmpty()) {
            setToastEffect("일정의 제목과 내용은 필수입니다.")
            return@launch
        }

        if (currentState.startTime >= currentState.endTime) {
            setToastEffect("일정의 시작 시간은 끝 시간보다 크거나 같을 수 없습니다.")
            return@launch
        }

        if (!canHandleClick(CREATE_PERSONAL_SCHEDULE)) return@launch

        setState {
            copy(isLoading = true)
        }

        postPersonalScheduleUseCase(
            ScheduleModel(
                title = currentState.title,
                content = currentState.content,
                scheduleDate = DateUtils.getServerFormat(currentState.date),
                startTime = currentState.startTime,
                endTime = currentState.endTime
            )
        ).onSuccess {
            setEffect(AddPersonalScheduleSideEffect.SuccessCreatePersonalSchedule(it))
        }.onFailure {

        }.onException { exception ->
            throw exception
        }

        setState {
            copy(isLoading = false)
        }
    }

    private fun onCreationFailure(message: String) {
        setToastEffect(message)
    }

    companion object {
        private const val CREATE_PERSONAL_SCHEDULE = "createPersonalScheduleClick"
    }

}