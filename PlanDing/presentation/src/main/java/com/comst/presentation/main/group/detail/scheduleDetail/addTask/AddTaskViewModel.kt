package com.comst.presentation.main.group.detail.scheduleDetail.addTask

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.comst.domain.usecase.group.GetGroupInformationUseCase
import com.comst.domain.usecase.groupSchedule.GetGroupScheduleUseCase
import com.comst.domain.util.onException
import com.comst.domain.util.onFailure
import com.comst.domain.util.onSuccess
import com.comst.presentation.common.base.BaseViewModel
import com.comst.presentation.main.group.detail.scheduleDetail.addTask.AddTaskContract.*
import com.comst.presentation.model.group.toTaskUserUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddTaskViewModel @Inject constructor(
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
        }
    }

    fun initialize(groupCode: String, scheduleId: Long) = viewModelScope.launch(apiExceptionHandler) {
        setState {
            copy(
                groupCode = groupCode,
                scheduleId = scheduleId
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

}