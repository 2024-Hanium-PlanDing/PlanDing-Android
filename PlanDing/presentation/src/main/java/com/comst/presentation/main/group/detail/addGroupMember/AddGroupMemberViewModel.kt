package com.comst.presentation.main.group.detail.addGroupMember

import com.comst.presentation.common.base.BaseViewModel
import com.comst.presentation.main.group.detail.addGroupMember.AddGroupMemberContract.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddGroupMemberViewModel @Inject constructor(

) : BaseViewModel<AddGroupMemberUIState, AddGroupMemberSideEffect, AddGroupMemberIntent, AddGroupMemberEvent>(AddGroupMemberUIState()) {

    override fun handleIntent(intent: AddGroupMemberIntent) {
        when(intent){
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

    private fun onInviteGroupMember(){

    }

}