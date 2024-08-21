package com.comst.presentation.main.mypage.groupRequestsReceived

import androidx.lifecycle.viewModelScope
import com.comst.domain.usecase.groupInvite.GetGroupRequestReceivedListUseCase
import com.comst.domain.util.onFailure
import com.comst.domain.util.onSuccess
import com.comst.presentation.common.base.BaseViewModel
import com.comst.presentation.main.mypage.groupRequestsReceived.GroupRequestsReceivedContract.*
import com.comst.presentation.model.mypage.groupRequestsReceived.toGroupRequestReceivedCardModelUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupRequestsReceivedViewModel @Inject constructor(
    private val getGroupRequestReceivedListUseCase: GetGroupRequestReceivedListUseCase
): BaseViewModel<GroupRequestsReceivedUIState, GroupRequestsReceivedSideEffect, GroupRequestsReceivedIntent, GroupRequestsReceivedEvent>(
    GroupRequestsReceivedUIState()
){
    init {
        load()
    }

    private fun load() = viewModelScope.launch {
        getGroupRequestReceivedListUseCase().onSuccess { groupRequestReceivedResponseModel ->
            setState {
                copy(
                    groupRequestReceivedList = groupRequestReceivedResponseModel.map { it.toGroupRequestReceivedCardModelUIModel() }
                )
            }
        }.onFailure {

        }
    }

    override fun handleIntent(intent: GroupRequestsReceivedIntent) {

    }

    override fun handleEvent(event: GroupRequestsReceivedEvent) {

    }

}