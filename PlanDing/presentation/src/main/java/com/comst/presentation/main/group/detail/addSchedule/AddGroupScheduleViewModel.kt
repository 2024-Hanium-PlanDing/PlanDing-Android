package com.comst.presentation.main.group.detail.addSchedule

import androidx.lifecycle.viewModelScope
import com.comst.domain.model.base.ScheduleModel
import com.comst.domain.usecase.local.GetUserCodeUseCase
import com.comst.domain.util.DateUtils
import com.comst.domain.util.onFailure
import com.comst.domain.util.onSuccess
import com.comst.presentation.common.base.BaseViewModel
import com.comst.presentation.main.group.detail.addSchedule.AddGroupScheduleContract.*
import com.comst.presentation.model.group.GroupProfileUIModel
import com.comst.presentation.model.group.socket.SendCreateScheduleDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class AddGroupScheduleViewModel @Inject constructor(
    private val getUserCodeUseCase: GetUserCodeUseCase
) : BaseViewModel<AddGroupScheduleUIState, AddGroupScheduleSideEffect, AddGroupScheduleIntent, AddGroupScheduleEvent>(AddGroupScheduleUIState()) {
    override fun handleIntent(intent: AddGroupScheduleIntent) {
        when (intent) {
            is AddGroupScheduleIntent.DescriptionChange -> onDescriptionChange(intent.description)
            is AddGroupScheduleIntent.SelectedEndTime -> onSelectedEndTime(intent.endTime)
            is AddGroupScheduleIntent.SelectedStartTime -> onSelectedStartTime(intent.startTime)
            is AddGroupScheduleIntent.TitleChange -> onTitleChange(intent.title)
            is AddGroupScheduleIntent.CreateGroupSchedule -> onCreateScheduleClick()
            is AddGroupScheduleIntent.Initialize -> initialize(groupProfile = intent.groupProfile, date = intent.date)
        }
    }

    override fun handleEvent(event: AddGroupScheduleEvent) {
        when (event) {
            is AddGroupScheduleEvent.CreationFailure -> onCreationFailure(event.message)
        }
    }

    fun initialize(groupProfile: GroupProfileUIModel, date: LocalDate) {
        setState {
            copy(
                groupProfile = groupProfile,
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
            setToastEffect("일정의 제목과 내용은 필수입니다.")
            return@launch
        }

        if (currentState.startTime >= currentState.endTime) {
            setToastEffect("일정의 시작 시간은 끝 시간보다 크거나 같을 수 없습니다.")
            return@launch
        }

        setState {
            copy(isLoading = true)
        }
        getUserCodeUseCase().onSuccess { userCode ->
            userCode?.let {
                setEffect(
                    AddGroupScheduleSideEffect.SuccessCreateGroupSchedule(
                        SendCreateScheduleDTO(
                            userCode = it,
                            title = currentState.title,
                            content = currentState.content,
                            scheduleDate = DateUtils.getServerFormat(currentState.date),
                            startTime = currentState.startTime,
                            endTime = currentState.endTime
                        )
                    )
                )
            } ?: run {
                // 재로그인?
            }
        }.onFailure {

        }


        setState {
            copy(isLoading = false)
        }
    }

    private fun onCreationFailure(message: String) {
        setToastEffect(message)
    }

    override fun handleError(exception: Exception) {
        super.handleError(exception)
        setToastEffect(exception.message.orEmpty())
    }
}