package com.comst.presentation.main.group.detail

import androidx.lifecycle.viewModelScope
import com.comst.domain.usecase.group.GetGroupInformationUseCase
import com.comst.domain.util.onFailure
import com.comst.domain.util.onSuccess
import com.comst.presentation.common.base.BaseViewModel
import com.comst.presentation.main.group.detail.GroupDetailContract.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupDetailViewModel @Inject constructor(
    private val getGroupInformationUseCase: GetGroupInformationUseCase
): BaseViewModel<GroupDetailUIState, GroupDetailUISideEffect, GroupDetailUIEvent>(GroupDetailUIState()){

    override suspend fun handleEvent(event: GroupDetailUIEvent) {

    }

    fun initialize(groupId: Long)  = viewModelScope.launch {
        getGroupInformationUseCase(groupId).onSuccess {

        }.onFailure { statusCode, message ->

        }
    }
}