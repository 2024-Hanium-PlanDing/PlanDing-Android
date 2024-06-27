package com.comst.presentation.main.mypage

import com.comst.presentation.common.base.UIEvent
import com.comst.presentation.common.base.UISideEffect
import com.comst.presentation.common.base.UIState

class MyPageContract {

    data class MyPageUIState(
        val username: String = "",
        val userCode: String = "",
        val profileImageUrl: String? = null,
        val favoriteGroupsCount: String = "-1",
        val receivedGroupRequestsCount: String = "-1",
        val isLoading: Boolean = false
    ): UIState

    sealed class MyPageUISideEffect : UISideEffect {
        data class ShowToast(val message: String): MyPageUISideEffect()
    }

    sealed class MyPageUIEvent : UIEvent{

    }
}