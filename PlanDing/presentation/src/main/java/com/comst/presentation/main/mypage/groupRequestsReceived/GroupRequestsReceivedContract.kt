package com.comst.presentation.main.mypage.groupRequestsReceived

import androidx.compose.runtime.Immutable
import com.comst.presentation.common.base.BaseEvent
import com.comst.presentation.common.base.BaseIntent
import com.comst.presentation.common.base.BaseSideEffect
import com.comst.presentation.common.base.UIState
import com.comst.presentation.model.mypage.groupRequestsReceived.GroupRequestReceivedCardModel

class GroupRequestsReceivedContract {

    @Immutable
    data class GroupRequestsReceivedUIState(
        val groupRequestReceivedList: List<GroupRequestReceivedCardModel> = listOf()
    ): UIState

    sealed class GroupRequestsReceivedSideEffect: BaseSideEffect {

    }

    sealed class GroupRequestsReceivedIntent: BaseIntent {
        data class AcceptClick(val groupRequestReceivedCardModel: GroupRequestReceivedCardModel) : GroupRequestsReceivedIntent()
        data class DenyClick(val groupRequestReceivedCardModel: GroupRequestReceivedCardModel) : GroupRequestsReceivedIntent()
    }

    sealed class GroupRequestsReceivedEvent: BaseEvent {

    }
}