package com.comst.presentation.main.schedule.addSchedule

import androidx.lifecycle.viewModelScope
import com.comst.domain.model.base.ScheduleModel
import com.comst.domain.usecase.personalSchedule.PostPersonalScheduleUseCase
import com.comst.domain.util.DateUtils
import com.comst.domain.util.onFailure
import com.comst.domain.util.onSuccess
import com.comst.presentation.common.base.BaseViewModel
import com.comst.presentation.main.schedule.addSchedule.AddScheduleContract.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class AddScheduleViewModel @Inject constructor(
    private val postPersonalScheduleUseCase: PostPersonalScheduleUseCase
) : BaseViewModel<AddScheduleUIState, AddScheduleSideEffect, AddScheduleIntent, AddScheduleEvent>(AddScheduleUIState()) {

    override fun handleIntent(intent: AddScheduleIntent) {
        when (intent) {
            is AddScheduleIntent.DescriptionChange -> onDescriptionChange(intent.description)
            is AddScheduleIntent.SelectedEndTime -> onSelectedEndTime(intent.endTime)
            is AddScheduleIntent.SelectedStartTime -> onSelectedStartTime(intent.startTime)
            is AddScheduleIntent.TitleChange -> onTitleChange(intent.title)
            is AddScheduleIntent.CreateSchedule -> onCreateScheduleClick()
        }
    }

    override fun handleEvent(event: AddScheduleEvent) {
        when (event) {
            is AddScheduleEvent.CreationFailure -> onCreationFailure(event.message)
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

    private fun onCreateScheduleClick() = viewModelScope.launch {
        if (currentState.title.isEmpty() || currentState.content.isEmpty()) {
            setEffect(AddScheduleSideEffect.ShowToast("일정의 제목과 내용은 필수입니다."))
            return@launch
        }

        if (currentState.startTime >= currentState.endTime) {
            setEffect(AddScheduleSideEffect.ShowToast("일정의 시작 시간은 끝 시간보다 크거나 같을 수 없습니다."))
            return@launch
        }

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
            setEffect(AddScheduleSideEffect.SuccessCreateSchedule(it))
        }.onFailure {

        }

        setState {
            copy(isLoading = false)
        }
    }

    private fun onCreationFailure(message: String) {
        setEffect(AddScheduleSideEffect.ShowToast(message))
    }

    override fun handleError(exception: Exception) {
        super.handleError(exception)
        setEffect(AddScheduleSideEffect.ShowToast(exception.message.orEmpty()))
    }
}