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
) : BaseViewModel<GroupUIState, GroupUISideEffect, GroupUIEvent>(GroupUIState()){


    init {
        load()
    }

    override suspend fun handleEvent(event: GroupUIEvent) {
        when(event){
            is GroupUIEvent.GroupCardClick -> setEffect(GroupUISideEffect.NavigateToGroupDetailActivity(event.id))
            is GroupUIEvent.GroupCreateClick -> setEffect(GroupUISideEffect.NavigateToCreateGroupActivity)
        }
    }

    fun load() = viewModelScope.launch {
        setState { copy(isLoading = true) }
        getMyGroupsUseCase().onSuccess {
            setState {
                copy(
                    groupCardModels = it
                )
            }
        }.onFailure { statusCode, message ->

        }
        setState { copy(isLoading = false) }
    }



}