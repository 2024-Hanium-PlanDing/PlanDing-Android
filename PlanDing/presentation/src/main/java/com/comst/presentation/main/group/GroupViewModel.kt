package com.comst.presentation.main.group

import androidx.lifecycle.viewModelScope
import com.comst.domain.usecase.group.GetMyGroupsUseCase
import com.comst.domain.util.onException
import com.comst.domain.util.onFailure
import com.comst.domain.util.onSuccess
import com.comst.presentation.common.base.BaseViewModel
import com.comst.presentation.main.group.GroupContract.GroupEvent
import com.comst.presentation.main.group.GroupContract.GroupIntent
import com.comst.presentation.main.group.GroupContract.GroupSideEffect
import com.comst.presentation.main.group.GroupContract.GroupUIState
import com.comst.presentation.model.group.GroupCardUIModel
import com.comst.presentation.model.group.toGroupCardUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class GroupViewModel @Inject constructor(
    private val getMyGroupsUseCase: GetMyGroupsUseCase
) : BaseViewModel<GroupUIState, GroupSideEffect, GroupIntent, GroupEvent>(GroupUIState()) {

    init {
        load()
    }

    override fun handleIntent(intent: GroupIntent) {
        when (intent) {
            is GroupIntent.GroupCardClick -> setEffect(GroupSideEffect.NavigateToGroupDetailActivity(intent.groupCode))
            is GroupIntent.GroupCreateClick -> setEffect(GroupSideEffect.NavigateToCreateGroupActivity)
            is GroupIntent.Refresh -> onRefresh()
        }
    }

    override fun handleEvent(event: GroupEvent) {
        when (event) {
            is GroupEvent.LoadFailure -> setToastEffect(event.message)
            is GroupEvent.GroupCreated -> onAddGroup(event.groupCardUIModel)
        }
    }

    private fun load() = viewModelScope.launch(apiExceptionHandler) {
        setState { copy(isLoading = true) }
        getMyGroupsUseCase()
            .onSuccess { groupCardModels ->
                setState {
                    copy(
                        groupCardModels = groupCardModels.map { it.toGroupCardUIModel() },
                        isRefreshing = false
                    )
                }
            }
            .onFailure {
            }.onException { exception ->
                throw exception
            }
        setState {
            copy(
                isLoading = false,
                isRefreshing = false
            )
        }
    }

    private fun onRefresh() {
        setState { copy(isRefreshing = true) }
        load()
    }

    private fun onAddGroup(groupCardUIModel: GroupCardUIModel) {
        setState {
            copy(
                groupCardModels = listOf(groupCardUIModel) + currentState.groupCardModels,
                isRefreshing = false
            )
        }
    }
}