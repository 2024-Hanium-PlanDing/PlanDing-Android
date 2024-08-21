package com.comst.presentation.main.mypage.groupRequestsReceived

import androidx.compose.runtime.Immutable
import com.comst.presentation.common.base.BaseEvent
import com.comst.presentation.common.base.BaseIntent
import com.comst.presentation.common.base.BaseSideEffect
import com.comst.presentation.common.base.UIState

class GroupRequestsReceivedContract {

    @Immutable
    data class GroupRequestsReceivedUIState(
        val groupRequestsReceived: List<String> = listOf()
    ): UIState

    sealed class GroupRequestsReceivedSideEffect: BaseSideEffect {

    }

    sealed class GroupRequestsReceivedIntent: BaseIntent {

    }

    sealed class GroupRequestsReceivedEvent: BaseEvent {

    }
}