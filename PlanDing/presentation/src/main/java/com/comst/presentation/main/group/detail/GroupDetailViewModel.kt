package com.comst.presentation.main.group.detail

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.comst.domain.usecase.group.GetGroupInformationUseCase
import com.comst.domain.util.onFailure
import com.comst.domain.util.onSuccess
import com.comst.presentation.common.base.BaseViewModel
import com.comst.presentation.main.group.detail.GroupDetailContract.*
import com.comst.presentation.model.group.toUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupDetailViewModel @Inject constructor(
    private val getGroupInformationUseCase: GetGroupInformationUseCase,
) : BaseViewModel<GroupDetailUIState, GroupDetailSideEffect, GroupDetailIntent, GroupDetailEvent>(GroupDetailUIState()) {

    override fun handleIntent(intent: GroupDetailIntent) {

    }

    override fun handleEvent(event: GroupDetailEvent) {

    }

    fun initialize(groupCode: String) = viewModelScope.launch {
        setState { copy(isLoading = true) }
        getGroupInformationUseCase(groupCode).onSuccess {
            setState {
                copy(
                    groupProfile =  it.toUIModel()
                )
            }
        }.onFailure {

        }
        setState { copy(isLoading = false) }
    }

    override fun handleError(exception: Exception) {
        super.handleError(exception)
        setToastEffect(exception.message.orEmpty())
    }
}