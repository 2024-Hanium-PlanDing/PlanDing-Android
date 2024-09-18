package com.comst.presentation.main.mypage

import androidx.compose.runtime.Immutable
import com.comst.presentation.common.base.BaseEvent
import com.comst.presentation.common.base.BaseIntent
import com.comst.presentation.common.base.BaseSideEffect
import com.comst.presentation.common.base.UIState

class MyPageContract {

    @Immutable
    data class MyPageUIState(
        val username: String = "",
        val userCode: String = "",
        val profileImageUrl: String? = null,
        val favoriteGroupsCount: String = "-1",
        val receivedGroupRequestsCount: String = "-1",
        val isLoading: Boolean = false,
        val isRefreshing: Boolean = false
    ) : UIState

    sealed class MyPageSideEffect : BaseSideEffect {
        object NavigateToGroupRequestsReceivedActivity: MyPageSideEffect()
        object NavigateToFavoriteActivity: MyPageSideEffect()
    }

    sealed class MyPageIntent : BaseIntent {
        object GroupRequestsReceivedClick: MyPageIntent()

        object FavoriteGroupClick: MyPageIntent()
        object Refresh : MyPageIntent()
    }

    sealed class MyPageEvent : BaseEvent {
        data class LoadFailure(val message: String) : MyPageEvent()
    }
}