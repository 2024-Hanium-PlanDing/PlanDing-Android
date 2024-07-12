package com.comst.presentation.main.group.detail

import androidx.compose.runtime.Immutable
import com.comst.presentation.common.base.BaseEvent
import com.comst.presentation.common.base.BaseIntent
import com.comst.presentation.common.base.BaseSideEffect
import com.comst.presentation.common.base.UIState

class GroupDetailContract {

    @Immutable
    data class GroupDetailUIState(
        val id: Long = 0,
    ) : UIState

    sealed class GroupDetailSideEffect : BaseSideEffect {

    }

    sealed class GroupDetailIntent : BaseIntent {
    }


    sealed class GroupDetailEvent : BaseEvent {

    }
}