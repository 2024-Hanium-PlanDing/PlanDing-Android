package com.comst.presentation.main.group.detail

import android.util.Log
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

    fun initialize(groupCode: String)  = viewModelScope.launch {
        getGroupInformationUseCase(groupCode).onSuccess {
            Log.d("하하", "${it}")
        }.onFailure { statusCode, message ->

        }
    }
}