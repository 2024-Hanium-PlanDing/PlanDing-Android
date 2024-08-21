package com.comst.presentation.main.group.detail.addGroupMember

import androidx.compose.runtime.Immutable
import com.comst.presentation.common.base.BaseEvent
import com.comst.presentation.common.base.BaseIntent
import com.comst.presentation.common.base.BaseSideEffect
import com.comst.presentation.common.base.UIState

class AddGroupMemberContract {

    @Immutable
    data class AddGroupMemberUIState(
        val groupCode: String = "",
        val userCode: String = ""
    ) : UIState

    sealed class AddGroupMemberSideEffect : BaseSideEffect{
        object SuccessInviteGroupMember : AddGroupMemberSideEffect()
    }

    sealed class AddGroupMemberIntent: BaseIntent {
        data class Initialize(val groupCode: String): AddGroupMemberIntent()
        object InviteGroupMember: AddGroupMemberIntent()
        data class UserCodeChange(val userCode: String): AddGroupMemberIntent()
    }

    sealed class AddGroupMemberEvent: BaseEvent{

    }
}