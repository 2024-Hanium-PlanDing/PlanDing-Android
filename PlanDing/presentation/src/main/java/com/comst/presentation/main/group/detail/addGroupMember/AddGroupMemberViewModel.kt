package com.comst.presentation.main.group.detail.addGroupMember

import androidx.lifecycle.viewModelScope
import com.comst.domain.model.group.GroupInviteModel
import com.comst.domain.usecase.groupInvite.PostGroupInviteUseCase
import com.comst.domain.util.onFailure
import com.comst.domain.util.onSuccess
import com.comst.presentation.common.base.BaseViewModel
import com.comst.presentation.main.group.detail.addGroupMember.AddGroupMemberContract.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddGroupMemberViewModel @Inject constructor(
    private val postGroupInviteUseCase: PostGroupInviteUseCase
) : BaseViewModel<AddGroupMemberUIState, AddGroupMemberSideEffect, AddGroupMemberIntent, AddGroupMemberEvent>(AddGroupMemberUIState()) {

    override fun handleIntent(intent: AddGroupMemberIntent) {
        when(intent){
            is AddGroupMemberIntent.Initialize -> initialize(intent.groupCode)
            is AddGroupMemberIntent.UserCodeChange -> onUserCodeChange(intent.userCode)
            is AddGroupMemberIntent.InviteGroupMember -> onInviteGroupMember()
        }
    }

    override fun handleEvent(event: AddGroupMemberEvent) {

    }

    fun initialize(groupCode: String){
        setState {
            copy(
                groupCode = groupCode,
                userCode = ""
            )
        }
    }

    private fun onUserCodeChange(userCode: String){
        setState {
            copy(
                userCode = userCode
            )
        }
    }

    private fun onInviteGroupMember() = viewModelScope.launch {
        if (currentState.userCode.isEmpty()){
            setToastEffect("유저 코드를 입력해주세요.")
            return@launch
        }
        postGroupInviteUseCase(
            GroupInviteModel(
                groupCode = currentState.groupCode,
                userCode =  currentState.userCode
            )
        ).onSuccess {
            setToastEffect("초대를 성공했습니다.")
            setEffect(AddGroupMemberSideEffect.SuccessInviteGroupMember)
        }.onFailure {

        }
    }

}