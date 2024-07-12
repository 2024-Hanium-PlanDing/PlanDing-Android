package com.comst.presentation.main.group

import androidx.lifecycle.viewModelScope
import com.comst.domain.usecase.group.GetMyGroupsUseCase
import com.comst.domain.util.onFailure
import com.comst.domain.util.onSuccess
import com.comst.presentation.common.base.BaseViewModel
import com.comst.presentation.main.group.GroupContract.*
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
        }
    }

    override fun handleEvent(event: GroupEvent) {
        when (event) {
            is GroupEvent.LoadFailure -> onLoadFailure(event.message)
        }
    }

    private fun load() = viewModelScope.launch {
        setState { copy(isLoading = true) }
        getMyGroupsUseCase()
            .onSuccess {
                setState {
                    copy(groupCardModels = it)
                }
            }
            .onFailure {
            }
        setState { copy(isLoading = false) }
    }

    private fun onLoadFailure(message: String) {
        setToastEffect(message)
    }

    override fun handleError(exception: Exception) {
        super.handleError(exception)
        setToastEffect(exception.message.orEmpty())
    }
}