package com.comst.presentation.main.group.detail.scheduleDetail.addTask

import androidx.lifecycle.viewModelScope
import com.comst.domain.model.groupSchedule.GroupScheduleResponseModel
import com.comst.domain.usecase.group.GetGroupInformationUseCase
import com.comst.domain.usecase.groupSchedule.GetGroupScheduleUseCase
import com.comst.domain.usecase.local.GetUserCodeUseCase
import com.comst.domain.util.DateUtils
import com.comst.domain.util.onException
import com.comst.domain.util.onFailure
import com.comst.domain.util.onSuccess
import com.comst.presentation.common.base.BaseViewModel
import com.comst.presentation.main.group.detail.scheduleDetail.ScheduleDetailContract
import com.comst.presentation.main.group.detail.scheduleDetail.addTask.AddTaskContract.AddTaskEvent
import com.comst.presentation.main.group.detail.scheduleDetail.addTask.AddTaskContract.AddTaskIntent
import com.comst.presentation.main.group.detail.scheduleDetail.addTask.AddTaskContract.AddTaskSideEffect
import com.comst.presentation.main.group.detail.scheduleDetail.addTask.AddTaskContract.AddTaskUIState
import com.comst.presentation.model.group.TaskUserUIModel
import com.comst.presentation.model.group.socket.SendCreateTaskDTO
import com.comst.presentation.model.group.toTaskUserUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddTaskViewModel @Inject constructor(
    private val getUserCodeUseCase: GetUserCodeUseCase,
    private val getGroupInformationUseCase: GetGroupInformationUseCase,
    private val getGroupScheduleUseCase: GetGroupScheduleUseCase
): BaseViewModel<AddTaskUIState, AddTaskSideEffect, AddTaskIntent, AddTaskEvent>(AddTaskUIState()){

    override fun handleIntent(intent: AddTaskIntent) {
        when(intent){
            is AddTaskIntent.Initialize -> initialize(
                groupCode = intent.groupCode,
                scheduleId = intent.scheduleId
            )
            is AddTaskIntent.TaskContentChange -> onTaskContentChange(intent.taskContent)
            is AddTaskIntent.TaskTitleChange -> onTaskTitleChange(intent.taskTitle)
            is AddTaskIntent.ToggleGroupMemberListVisibility -> onToggleGroupMemberListVisibility()
            is AddTaskIntent.SelectedDueTime -> onSelectedDueTime(intent.dueTime)
            is AddTaskIntent.MemberCheckBoxClick -> onMemberCheckBoxClick(intent.member)
            is AddTaskIntent.CreateTask -> onCreateTaskClick()
        }
    }

    fun initialize(groupCode: String, scheduleId: Long) = viewModelScope.launch(coroutineExceptionHandler) {
        setState {
            copy(
                groupCode = groupCode,
                scheduleId = scheduleId,
                userCode = "",
                dueTime = 6,
                schedule = GroupScheduleResponseModel(
                    id = 4357,
                    title = "facilisi",
                    content = "dicat",
                    scheduleDate = DateUtils.getCurrentDate(),
                    startTime = 9738,
                    endTime = 4338,
                    day = "duis",
                    type = "mea",
                    groupName = "Christian Rowe",
                    userScheduleAttendances = listOf()
                ),
                groupMember = listOf(),
                participantMember = listOf(),
                taskTitle = "",
                taskContent = "",
                isGroupMemberListVisible = false
            )
        }

        launch {
            getGroupInformationUseCase(currentState.groupCode).onSuccess { groupInfo ->
                setState {
                    copy(
                        groupMember = groupInfo.users.map { it.toTaskUserUIModel() }
                    )
                }
            }.onFailure {

            }.onException {  exception ->
                throw exception
            }
        }

        launch {
            getGroupScheduleUseCase(
                groupCode = currentState.groupCode,
                scheduleId = currentState.scheduleId
            ).onSuccess {
                setState {
                    copy(
                        schedule = it
                    )
                }
            }.onFailure {

            }.onException { exception ->
                throw exception
            }
        }
    }

    private fun onTaskTitleChange(taskTitle: String){
        setState {
            copy(
                taskTitle = taskTitle
            )
        }
    }

    private fun onTaskContentChange(taskContent: String){
        setState {
            copy(
                taskContent = taskContent
            )
        }
    }

    private fun onToggleGroupMemberListVisibility(){
        setState {
            copy(isGroupMemberListVisible = !currentState.isGroupMemberListVisible)
        }
    }

    private fun onSelectedDueTime(selectedDueTime: Int){
        setState {
            copy(
                dueTime = selectedDueTime
            )
        }
    }

    private fun onMemberCheckBoxClick(member: TaskUserUIModel){
        if (currentState.participantMember.contains(member)){
            setState {
                copy(
                    participantMember = participantMember - member
                )
            }
        }else{
            setState {
                copy(
                    participantMember = participantMember + member
                )
            }
        }
    }

    private fun onCreateTaskClick() = viewModelScope.launch(coroutineExceptionHandler){
        if (currentState.taskTitle.isEmpty() || currentState.taskContent.isEmpty()) {
            setToastEffect("할 일의 제목과 내용은 필수입니다.")
            return@launch
        }

        if (!canHandleClick(CREATE_GROUP_TASK)) return@launch

        getUserCodeUseCase().onSuccess { userCode ->
            userCode?.let { code ->
                val formattedDueTime = String.format("%02d:00:00", currentState.dueTime)
                val deadline = "${currentState.schedule.scheduleDate}T$formattedDueTime"
                setEffect(
                    AddTaskSideEffect.SuccessCreateTask(
                        SendCreateTaskDTO(
                            title = currentState.taskTitle,
                            content = currentState.taskContent,
                            deadline = deadline,
                            status = ScheduleDetailContract.TaskStatus.TODO.name,
                            managerCode = code,
                            userCodes = currentState.participantMember.map { it.userCode },
                            scheduleId = currentState.scheduleId
                        )
                    )
                )
            } ?: run {
                // 재로그인?
            }
        }.onFailure {

        }

    }

    companion object {
        private const val CREATE_GROUP_TASK = "createGroupTaskClick"
    }

}