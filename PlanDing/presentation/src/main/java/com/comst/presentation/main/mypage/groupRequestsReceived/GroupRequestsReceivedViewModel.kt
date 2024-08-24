package com.comst.presentation.main.mypage.groupRequestsReceived

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.comst.domain.usecase.groupInvite.DeleteDenyGroupInviteUseCase
import com.comst.domain.usecase.groupInvite.GetAcceptGroupInviteUseCase
import com.comst.domain.usecase.groupInvite.GetGroupRequestReceivedListUseCase
import com.comst.domain.util.onError
import com.comst.domain.util.onException
import com.comst.domain.util.onFailure
import com.comst.domain.util.onSuccess
import com.comst.presentation.common.base.BaseViewModel
import com.comst.presentation.main.mypage.groupRequestsReceived.GroupRequestsReceivedContract.*
import com.comst.presentation.model.mypage.groupRequestsReceived.GroupRequestReceivedCardModel
import com.comst.presentation.model.mypage.groupRequestsReceived.toGroupRequestReceivedCardModelUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupRequestsReceivedViewModel @Inject constructor(
    private val getGroupRequestReceivedListUseCase: GetGroupRequestReceivedListUseCase,
    private val getAcceptGroupInviteUseCase: GetAcceptGroupInviteUseCase,
    private val deleteDenyGroupInviteUseCase: DeleteDenyGroupInviteUseCase
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
        when(intent){
            is GroupRequestsReceivedIntent.AcceptClick -> onAcceptClick(intent.groupRequestReceivedCardModel)

            is GroupRequestsReceivedIntent.DenyClick -> onDenyClick(intent.groupRequestReceivedCardModel)
        }
    }

    override fun handleEvent(event: GroupRequestsReceivedEvent){

    }

    private fun onAcceptClick(groupRequestReceivedCardModel: GroupRequestReceivedCardModel) = viewModelScope.launch {
        getAcceptGroupInviteUseCase(
            groupCode = groupRequestReceivedCardModel.groupCode,
            inviteCode = groupRequestReceivedCardModel.inviteCode
        ).onSuccess {
            setState {
                copy(
                    groupRequestReceivedList = groupRequestReceivedList - groupRequestReceivedCardModel
                )
            }
            setToastEffect("요청을 수락했습니다.")
        }.onFailure {

        }
    }

    private fun onDenyClick(groupRequestReceivedCardModel: GroupRequestReceivedCardModel)  = viewModelScope.launch{
        deleteDenyGroupInviteUseCase(
            inviteCode = groupRequestReceivedCardModel.inviteCode
        ).onSuccess {
            setState {
                copy(
                    groupRequestReceivedList = groupRequestReceivedList - groupRequestReceivedCardModel
                )
            }
            setToastEffect("요청을 거절했습니다.")
        }.onFailure {
            
        }
    }

}